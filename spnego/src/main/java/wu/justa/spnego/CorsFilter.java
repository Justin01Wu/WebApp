package wu.justa.spnego;

import java.io.IOException;
import java.util.Arrays;
import java.util.Locale;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

public class CorsFilter implements Filter {

    private static final Logger LOG = Logger.getLogger(CorsFilter.class);

    private AtomicBoolean corsEnabled = new AtomicBoolean();
    private AtomicLong accessControlMaxAge = new AtomicLong(3600);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException { 
        boolean corsIsEnabled = enableCors(filterConfig.getInitParameter("cors-enabled"));

        if (corsIsEnabled) {
            try {
                configureCorsMaxAge(filterConfig.getInitParameter("cors-max-age-timeunit"),
                    filterConfig.getInitParameter("cors-max-age-duration"));
            } catch (NumberFormatException e) {
                throw new ServletException("Invalid number set for cors-max-age-duration");
            } catch (IllegalArgumentException e) {
                throw new ServletException(
                        String.format("Valid values for cors-max-age-timeunit are: %s.",
                                Arrays.toString(TimeUnit.values())));
            }
        }

        LOG.info(String.format("Cors settings: enabled[%s], max-age-seconds[%s]", corsIsEnabled, accessControlMaxAge));
    }
    
    private boolean enableCors(String corsEnabledText) {
        if (corsEnabledText == null || corsEnabledText.trim().isEmpty())
            corsEnabledText = null;

        boolean oldValue = corsEnabled.get();
        boolean newValue = Boolean.parseBoolean(Optional.ofNullable(corsEnabledText).orElse("false"));
        corsEnabled.compareAndSet(oldValue, newValue);

        return corsEnabled.get();
    }
    
    private void configureCorsMaxAge(String corsMaxAgeTimeUnitText, String corsMaxAgeDurationText) {
        if (corsMaxAgeTimeUnitText == null || corsMaxAgeTimeUnitText.trim().isEmpty())
            corsMaxAgeTimeUnitText = null;

        if (corsMaxAgeDurationText == null || corsMaxAgeDurationText.trim().isEmpty())
            corsMaxAgeDurationText = null;

        long oldValue = accessControlMaxAge.get(); 
        corsMaxAgeTimeUnitText = Optional.ofNullable(corsMaxAgeTimeUnitText).orElse(TimeUnit.SECONDS.name()).trim().toUpperCase(Locale.ENGLISH);
        corsMaxAgeDurationText = Optional.ofNullable(corsMaxAgeDurationText).orElse(String.valueOf(oldValue)).trim();

        TimeUnit timeUnit = TimeUnit.valueOf(corsMaxAgeTimeUnitText);
        long duration = Long.valueOf(corsMaxAgeDurationText);
        long newValue = TimeUnit.SECONDS.convert(duration, timeUnit);

        accessControlMaxAge.compareAndSet(oldValue, newValue); 
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {    	
    	
        HttpServletRequest servletRequest = (HttpServletRequest) request;
        HttpServletResponse servletResponse = (HttpServletResponse) response;
        
        StringBuffer requestURL = servletRequest.getRequestURL();
        LOG.info(servletRequest.getMethod() +": " + requestURL.toString());

        if (corsEnabled.get()) {
            applyCorsHeaders(servletResponse);

            if (isOptionRequest(servletRequest)) {
                servletResponse.setStatus(HttpServletResponse.SC_OK);
                return;
            }
        }

        chain.doFilter(servletRequest, servletResponse);
    }

    private boolean isOptionRequest(HttpServletRequest request) {
        return request.getMethod().equalsIgnoreCase("OPTIONS");
    }

    private void applyCorsHeaders(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        //response.addHeader("Access-Control-Allow-Headers", "Authorization");
        // response.addHeader("Access-Control-Allow-Credentials", "true");
        //response.addHeader("Access-Control-Max-Age", accessControlMaxAge.toString());
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, PATCH, DELETE, OPTIONS, HEAD");
    }

    @Override
    public void destroy() {}

}
