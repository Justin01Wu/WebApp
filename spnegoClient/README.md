Spnego Client test 
=========

+ This is a demo on how to using Spnego authenticate center

## Sample JavaScript Code:

## Sample VBA code:
```
Sub TestToken()

    Dim winHttp As Object
    Set winHttp = CreateObject("WinHttp.WinHttpRequest.5.1")
    winHttp.SetAutoLogonPolicy (0)    
    Rem winHttp.Open "GET", "http://uspasicsapts01:8380/spnego/JWTToken"
    winHttp.Open "GET", "http://localhost:8080/vcaps3/api/v2/support/currencies.json"
    winHttp.send
    a = winHttp.responseText
    MsgBox a
        
End Sub
```


# JWT Token 
+ JSON Web Tokens (JWT) are an open, industry standard RFC 7519 method for representing claims securely between two parties.
+ JWT.IO allows you to decode, verify and generate JWT.
+ JWT token has 3 parties, every part are separated by a ".", which are called header, payload and signature
+ three parties are encoded with base64 in order to pass it on URL
+ The header typically consists of two parts: the type of the token, which is JWT, and the signing algorithm being used, such as HMAC SHA256 or RSA.
+ The token can be automatically verified when it is RSA, because the client side can get public key automatically and verify it, based on this specification: https://ldapwiki.com/wiki/Openid-configuration
+ For example: given a token, its issuers is https://ac01.abc.com:8443/auth/realms/vcaps-test/, 
    + then client side will get server info from https://ac01.abc.com:8443/auth/realms/vcaps-test/.well-known/openid-configuration
    + client side will get server certs from https://ac01.abc.com:8443/auth/realms/vcaps-test/protocol/openid-connect/certs 
    + Client side will verify this token based on the certs.
    + Of course, client side need to set white list for servers
