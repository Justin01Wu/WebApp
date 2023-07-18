(window["webpackJsonp"] = window["webpackJsonp"] || []).push([["main"],{

/***/ "../shared/src/lib/TypeMessage.ts":
/*!****************************************!*\
  !*** ../shared/src/lib/TypeMessage.ts ***!
  \****************************************/
/*! exports provided: TypeMessage */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "TypeMessage", function() { return TypeMessage; });
class TypeMessage {
}


/***/ }),

/***/ "../shared/src/lib/enums.ts":
/*!**********************************!*\
  !*** ../shared/src/lib/enums.ts ***!
  \**********************************/
/*! exports provided: DataType, ProfitType, OProfitType */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "DataType", function() { return DataType; });
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "ProfitType", function() { return ProfitType; });
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "OProfitType", function() { return OProfitType; });
var DataType;
(function (DataType) {
    DataType["Nominal"] = "Nominal";
    DataType["Percentage"] = "Percentage";
})(DataType || (DataType = {}));
var ProfitType;
(function (ProfitType) {
    ProfitType[ProfitType["SmartPhone"] = 11] = "SmartPhone";
    ProfitType[ProfitType["Computer"] = 22] = "Computer";
    ProfitType[ProfitType["GameBox"] = 33] = "GameBox";
})(ProfitType || (ProfitType = {}));
// const vs enum:  https://www.typescriptlang.org/docs/handbook/enums.html
const OProfitType = {
    SmartPhone: 11,
    Computer: 22,
    GameBox: 33
};


/***/ }),

/***/ "../shared/src/lib/profit-name.pipe.ts":
/*!*********************************************!*\
  !*** ../shared/src/lib/profit-name.pipe.ts ***!
  \*********************************************/
/*! exports provided: ProfitNamePipe */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "ProfitNamePipe", function() { return ProfitNamePipe; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "../../node_modules/@angular/core/__ivy_ngcc__/fesm2015/core.js");


class ProfitNamePipe {
    transform(value, ...args) {
        if (value === 1) {
            return 'Smart Phone';
        }
        if (value === 2) {
            return 'Computer';
        }
        if (value === 3) {
            return 'Game Box';
        }
        return '';
    }
}
ProfitNamePipe.ɵfac = function ProfitNamePipe_Factory(t) { return new (t || ProfitNamePipe)(); };
ProfitNamePipe.ɵpipe = _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵdefinePipe"]({ name: "profitName", type: ProfitNamePipe, pure: true });
/*@__PURE__*/ (function () { _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵsetClassMetadata"](ProfitNamePipe, [{
        type: _angular_core__WEBPACK_IMPORTED_MODULE_0__["Pipe"],
        args: [{
                name: 'profitName'
            }]
    }], null, null); })();


/***/ }),

/***/ "../shared/src/lib/safe-url.pipe.ts":
/*!******************************************!*\
  !*** ../shared/src/lib/safe-url.pipe.ts ***!
  \******************************************/
/*! exports provided: SafeUrlPipe */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "SafeUrlPipe", function() { return SafeUrlPipe; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "../../node_modules/@angular/core/__ivy_ngcc__/fesm2015/core.js");
/* harmony import */ var _angular_platform_browser__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/platform-browser */ "../../node_modules/@angular/platform-browser/__ivy_ngcc__/fesm2015/platform-browser.js");



class SafeUrlPipe {
    constructor(domSanitizer) {
        this.domSanitizer = domSanitizer;
    }
    transform(url) {
        const r = this.domSanitizer.bypassSecurityTrustUrl(url);
        return r;
    }
}
SafeUrlPipe.ɵfac = function SafeUrlPipe_Factory(t) { return new (t || SafeUrlPipe)(_angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵdirectiveInject"](_angular_platform_browser__WEBPACK_IMPORTED_MODULE_1__["DomSanitizer"])); };
SafeUrlPipe.ɵpipe = _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵdefinePipe"]({ name: "safeUrl", type: SafeUrlPipe, pure: true });
/*@__PURE__*/ (function () { _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵsetClassMetadata"](SafeUrlPipe, [{
        type: _angular_core__WEBPACK_IMPORTED_MODULE_0__["Pipe"],
        args: [{
                name: 'safeUrl'
            }]
    }], function () { return [{ type: _angular_platform_browser__WEBPACK_IMPORTED_MODULE_1__["DomSanitizer"] }]; }, null); })();


/***/ }),

/***/ "../shared/src/lib/shared.component.ts":
/*!*********************************************!*\
  !*** ../shared/src/lib/shared.component.ts ***!
  \*********************************************/
/*! exports provided: SharedComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "SharedComponent", function() { return SharedComponent; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "../../node_modules/@angular/core/__ivy_ngcc__/fesm2015/core.js");


class SharedComponent {
    constructor() { }
    ngOnInit() {
    }
}
SharedComponent.ɵfac = function SharedComponent_Factory(t) { return new (t || SharedComponent)(); };
SharedComponent.ɵcmp = _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵdefineComponent"]({ type: SharedComponent, selectors: [["lib-shared"]], decls: 2, vars: 0, template: function SharedComponent_Template(rf, ctx) { if (rf & 1) {
        _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵelementStart"](0, "p");
        _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵtext"](1, " shared works! ");
        _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵelementEnd"]();
    } }, encapsulation: 2 });
/*@__PURE__*/ (function () { _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵsetClassMetadata"](SharedComponent, [{
        type: _angular_core__WEBPACK_IMPORTED_MODULE_0__["Component"],
        args: [{
                selector: 'lib-shared',
                template: `
    <p>
      shared works!
    </p>
  `,
                styles: []
            }]
    }], function () { return []; }, null); })();


/***/ }),

/***/ "../shared/src/lib/shared.module.ts":
/*!******************************************!*\
  !*** ../shared/src/lib/shared.module.ts ***!
  \******************************************/
/*! exports provided: SharedModule */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "SharedModule", function() { return SharedModule; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "../../node_modules/@angular/core/__ivy_ngcc__/fesm2015/core.js");
/* harmony import */ var _profit_name_pipe__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ./profit-name.pipe */ "../shared/src/lib/profit-name.pipe.ts");
/* harmony import */ var _safe_url_pipe__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ./safe-url.pipe */ "../shared/src/lib/safe-url.pipe.ts");
/* harmony import */ var _shared_component__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! ./shared.component */ "../shared/src/lib/shared.component.ts");





class SharedModule {
}
SharedModule.ɵmod = _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵdefineNgModule"]({ type: SharedModule });
SharedModule.ɵinj = _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵdefineInjector"]({ factory: function SharedModule_Factory(t) { return new (t || SharedModule)(); }, imports: [[]] });
(function () { (typeof ngJitMode === "undefined" || ngJitMode) && _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵsetNgModuleScope"](SharedModule, { declarations: [_shared_component__WEBPACK_IMPORTED_MODULE_3__["SharedComponent"],
        _profit_name_pipe__WEBPACK_IMPORTED_MODULE_1__["ProfitNamePipe"],
        _safe_url_pipe__WEBPACK_IMPORTED_MODULE_2__["SafeUrlPipe"]], exports: [_shared_component__WEBPACK_IMPORTED_MODULE_3__["SharedComponent"],
        _profit_name_pipe__WEBPACK_IMPORTED_MODULE_1__["ProfitNamePipe"],
        _safe_url_pipe__WEBPACK_IMPORTED_MODULE_2__["SafeUrlPipe"]] }); })();
/*@__PURE__*/ (function () { _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵsetClassMetadata"](SharedModule, [{
        type: _angular_core__WEBPACK_IMPORTED_MODULE_0__["NgModule"],
        args: [{
                declarations: [
                    _shared_component__WEBPACK_IMPORTED_MODULE_3__["SharedComponent"],
                    _profit_name_pipe__WEBPACK_IMPORTED_MODULE_1__["ProfitNamePipe"],
                    _safe_url_pipe__WEBPACK_IMPORTED_MODULE_2__["SafeUrlPipe"]
                ],
                imports: [],
                exports: [
                    _shared_component__WEBPACK_IMPORTED_MODULE_3__["SharedComponent"],
                    _profit_name_pipe__WEBPACK_IMPORTED_MODULE_1__["ProfitNamePipe"],
                    _safe_url_pipe__WEBPACK_IMPORTED_MODULE_2__["SafeUrlPipe"]
                ]
            }]
    }], null, null); })();


/***/ }),

/***/ "../shared/src/lib/shared.service.ts":
/*!*******************************************!*\
  !*** ../shared/src/lib/shared.service.ts ***!
  \*******************************************/
/*! exports provided: SharedService */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "SharedService", function() { return SharedService; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "../../node_modules/@angular/core/__ivy_ngcc__/fesm2015/core.js");


class SharedService {
    constructor() { }
    getServerName() {
        return 'from shared mudule SharedService';
    }
}
SharedService.ɵfac = function SharedService_Factory(t) { return new (t || SharedService)(); };
SharedService.ɵprov = _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵdefineInjectable"]({ token: SharedService, factory: SharedService.ɵfac, providedIn: 'root' });
/*@__PURE__*/ (function () { _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵsetClassMetadata"](SharedService, [{
        type: _angular_core__WEBPACK_IMPORTED_MODULE_0__["Injectable"],
        args: [{
                providedIn: 'root'
            }]
    }], function () { return []; }, null); })();


/***/ }),

/***/ "../shared/src/public-api.ts":
/*!***********************************!*\
  !*** ../shared/src/public-api.ts ***!
  \***********************************/
/*! exports provided: SharedService, SharedComponent, SharedModule, DataType, ProfitType, OProfitType, ProfitNamePipe, SafeUrlPipe, TypeMessage */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony import */ var _lib_shared_service__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! ./lib/shared.service */ "../shared/src/lib/shared.service.ts");
/* harmony reexport (safe) */ __webpack_require__.d(__webpack_exports__, "SharedService", function() { return _lib_shared_service__WEBPACK_IMPORTED_MODULE_0__["SharedService"]; });

/* harmony import */ var _lib_shared_component__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ./lib/shared.component */ "../shared/src/lib/shared.component.ts");
/* harmony reexport (safe) */ __webpack_require__.d(__webpack_exports__, "SharedComponent", function() { return _lib_shared_component__WEBPACK_IMPORTED_MODULE_1__["SharedComponent"]; });

/* harmony import */ var _lib_shared_module__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ./lib/shared.module */ "../shared/src/lib/shared.module.ts");
/* harmony reexport (safe) */ __webpack_require__.d(__webpack_exports__, "SharedModule", function() { return _lib_shared_module__WEBPACK_IMPORTED_MODULE_2__["SharedModule"]; });

/* harmony import */ var _lib_enums__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! ./lib/enums */ "../shared/src/lib/enums.ts");
/* harmony reexport (safe) */ __webpack_require__.d(__webpack_exports__, "DataType", function() { return _lib_enums__WEBPACK_IMPORTED_MODULE_3__["DataType"]; });

/* harmony reexport (safe) */ __webpack_require__.d(__webpack_exports__, "ProfitType", function() { return _lib_enums__WEBPACK_IMPORTED_MODULE_3__["ProfitType"]; });

/* harmony reexport (safe) */ __webpack_require__.d(__webpack_exports__, "OProfitType", function() { return _lib_enums__WEBPACK_IMPORTED_MODULE_3__["OProfitType"]; });

/* harmony import */ var _lib_profit_name_pipe__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! ./lib/profit-name.pipe */ "../shared/src/lib/profit-name.pipe.ts");
/* harmony reexport (safe) */ __webpack_require__.d(__webpack_exports__, "ProfitNamePipe", function() { return _lib_profit_name_pipe__WEBPACK_IMPORTED_MODULE_4__["ProfitNamePipe"]; });

/* harmony import */ var _lib_safe_url_pipe__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(/*! ./lib/safe-url.pipe */ "../shared/src/lib/safe-url.pipe.ts");
/* harmony reexport (safe) */ __webpack_require__.d(__webpack_exports__, "SafeUrlPipe", function() { return _lib_safe_url_pipe__WEBPACK_IMPORTED_MODULE_5__["SafeUrlPipe"]; });

/* harmony import */ var _lib_TypeMessage__WEBPACK_IMPORTED_MODULE_6__ = __webpack_require__(/*! ./lib/TypeMessage */ "../shared/src/lib/TypeMessage.ts");
/* harmony reexport (safe) */ __webpack_require__.d(__webpack_exports__, "TypeMessage", function() { return _lib_TypeMessage__WEBPACK_IMPORTED_MODULE_6__["TypeMessage"]; });

/*
 * Public API Surface of shared
 */









/***/ }),

/***/ "./$$_lazy_route_resource lazy recursive":
/*!******************************************************!*\
  !*** ./$$_lazy_route_resource lazy namespace object ***!
  \******************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

function webpackEmptyAsyncContext(req) {
	// Here Promise.resolve().then() is used instead of new Promise() to prevent
	// uncaught exception popping up in devtools
	return Promise.resolve().then(function() {
		var e = new Error("Cannot find module '" + req + "'");
		e.code = 'MODULE_NOT_FOUND';
		throw e;
	});
}
webpackEmptyAsyncContext.keys = function() { return []; };
webpackEmptyAsyncContext.resolve = webpackEmptyAsyncContext;
module.exports = webpackEmptyAsyncContext;
webpackEmptyAsyncContext.id = "./$$_lazy_route_resource lazy recursive";

/***/ }),

/***/ "./src/app/app.component.ts":
/*!**********************************!*\
  !*** ./src/app/app.component.ts ***!
  \**********************************/
/*! exports provided: AppComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "AppComponent", function() { return AppComponent; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "../../node_modules/@angular/core/__ivy_ngcc__/fesm2015/core.js");


class AppComponent {
    constructor() {
        this.title = 'comp2';
    }
}
AppComponent.ɵfac = function AppComponent_Factory(t) { return new (t || AppComponent)(); };
AppComponent.ɵcmp = _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵdefineComponent"]({ type: AppComponent, selectors: [["app-root"]], decls: 0, vars: 0, template: function AppComponent_Template(rf, ctx) { }, encapsulation: 2 });
/*@__PURE__*/ (function () { _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵsetClassMetadata"](AppComponent, [{
        type: _angular_core__WEBPACK_IMPORTED_MODULE_0__["Component"],
        args: [{
                selector: 'app-root',
                template: '',
                styleUrls: []
            }]
    }], null, null); })();


/***/ }),

/***/ "./src/app/app.module.ts":
/*!*******************************!*\
  !*** ./src/app/app.module.ts ***!
  \*******************************/
/*! exports provided: AppModule */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "AppModule", function() { return AppModule; });
/* harmony import */ var _angular_platform_browser__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/platform-browser */ "../../node_modules/@angular/platform-browser/__ivy_ngcc__/fesm2015/platform-browser.js");
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/core */ "../../node_modules/@angular/core/__ivy_ngcc__/fesm2015/core.js");
/* harmony import */ var _app_component__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ./app.component */ "./src/app/app.component.ts");
/* harmony import */ var _profit_dropdown_profit_dropdown_component__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! ./profit-dropdown/profit-dropdown.component */ "./src/app/profit-dropdown/profit-dropdown.component.ts");
/* harmony import */ var _angular_elements__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! @angular/elements */ "../../node_modules/@angular/elements/__ivy_ngcc__/fesm2015/elements.js");
/* harmony import */ var _fire_justa_fire_component__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(/*! ./fire/justa-fire.component */ "./src/app/fire/justa-fire.component.ts");
/* harmony import */ var _multi_user_picker_multi_user_picker_component__WEBPACK_IMPORTED_MODULE_6__ = __webpack_require__(/*! ./multi-user-picker/multi-user-picker.component */ "./src/app/multi-user-picker/multi-user-picker.component.ts");
/* harmony import */ var _angular_common_http__WEBPACK_IMPORTED_MODULE_7__ = __webpack_require__(/*! @angular/common/http */ "../../node_modules/@angular/common/__ivy_ngcc__/fesm2015/http.js");
/* harmony import */ var _angular_forms__WEBPACK_IMPORTED_MODULE_8__ = __webpack_require__(/*! @angular/forms */ "../../node_modules/@angular/forms/__ivy_ngcc__/fesm2015/forms.js");










class AppModule {
    constructor(injector) {
        this.injector = injector;
        const elements = {
            'profit-dropdown': _profit_dropdown_profit_dropdown_component__WEBPACK_IMPORTED_MODULE_3__["ProfitDropdownComponent"],
            'justa-fire': _fire_justa_fire_component__WEBPACK_IMPORTED_MODULE_5__["JustaFireComponent"],
            'justa-user-picker': _multi_user_picker_multi_user_picker_component__WEBPACK_IMPORTED_MODULE_6__["MultiUserPickerComponent"],
        };
        Object.keys(elements).forEach((k) => {
            const component = elements[k];
            const e = Object(_angular_elements__WEBPACK_IMPORTED_MODULE_4__["createCustomElement"])(component, {
                injector: this.injector,
            });
            customElements.define(k, e);
        });
    }
    ngDoBootstrap() { }
}
AppModule.ɵmod = _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵdefineNgModule"]({ type: AppModule });
AppModule.ɵinj = _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵdefineInjector"]({ factory: function AppModule_Factory(t) { return new (t || AppModule)(_angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵinject"](_angular_core__WEBPACK_IMPORTED_MODULE_1__["Injector"])); }, providers: [], imports: [[
            _angular_platform_browser__WEBPACK_IMPORTED_MODULE_0__["BrowserModule"],
            _angular_common_http__WEBPACK_IMPORTED_MODULE_7__["HttpClientModule"],
            // MultiUserPickerComponent need HttpClient in the constructor,
            // but we need to import HttpClientModule rather than HttpClient
            // which is weird
            _angular_forms__WEBPACK_IMPORTED_MODULE_8__["FormsModule"],
            _angular_forms__WEBPACK_IMPORTED_MODULE_8__["ReactiveFormsModule"] // this is needed for angular forms
        ]] });
(function () { (typeof ngJitMode === "undefined" || ngJitMode) && _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵɵsetNgModuleScope"](AppModule, { declarations: [_app_component__WEBPACK_IMPORTED_MODULE_2__["AppComponent"],
        _profit_dropdown_profit_dropdown_component__WEBPACK_IMPORTED_MODULE_3__["ProfitDropdownComponent"],
        _fire_justa_fire_component__WEBPACK_IMPORTED_MODULE_5__["JustaFireComponent"],
        _multi_user_picker_multi_user_picker_component__WEBPACK_IMPORTED_MODULE_6__["MultiUserPickerComponent"]], imports: [_angular_platform_browser__WEBPACK_IMPORTED_MODULE_0__["BrowserModule"],
        _angular_common_http__WEBPACK_IMPORTED_MODULE_7__["HttpClientModule"],
        // MultiUserPickerComponent need HttpClient in the constructor,
        // but we need to import HttpClientModule rather than HttpClient
        // which is weird
        _angular_forms__WEBPACK_IMPORTED_MODULE_8__["FormsModule"],
        _angular_forms__WEBPACK_IMPORTED_MODULE_8__["ReactiveFormsModule"] // this is needed for angular forms
    ] }); })();
/*@__PURE__*/ (function () { _angular_core__WEBPACK_IMPORTED_MODULE_1__["ɵsetClassMetadata"](AppModule, [{
        type: _angular_core__WEBPACK_IMPORTED_MODULE_1__["NgModule"],
        args: [{
                declarations: [
                    _app_component__WEBPACK_IMPORTED_MODULE_2__["AppComponent"],
                    _profit_dropdown_profit_dropdown_component__WEBPACK_IMPORTED_MODULE_3__["ProfitDropdownComponent"],
                    _fire_justa_fire_component__WEBPACK_IMPORTED_MODULE_5__["JustaFireComponent"],
                    _multi_user_picker_multi_user_picker_component__WEBPACK_IMPORTED_MODULE_6__["MultiUserPickerComponent"]
                ],
                imports: [
                    _angular_platform_browser__WEBPACK_IMPORTED_MODULE_0__["BrowserModule"],
                    _angular_common_http__WEBPACK_IMPORTED_MODULE_7__["HttpClientModule"],
                    // MultiUserPickerComponent need HttpClient in the constructor,
                    // but we need to import HttpClientModule rather than HttpClient
                    // which is weird
                    _angular_forms__WEBPACK_IMPORTED_MODULE_8__["FormsModule"],
                    _angular_forms__WEBPACK_IMPORTED_MODULE_8__["ReactiveFormsModule"] // this is needed for angular forms
                ],
                providers: [],
            }]
    }], function () { return [{ type: _angular_core__WEBPACK_IMPORTED_MODULE_1__["Injector"] }]; }, null); })();


/***/ }),

/***/ "./src/app/common/LocalSetting.ts":
/*!****************************************!*\
  !*** ./src/app/common/LocalSetting.ts ***!
  \****************************************/
/*! exports provided: LocalSetting */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "LocalSetting", function() { return LocalSetting; });
class LocalSetting {
    static getLoggin() {
        if (LocalSetting.logging !== undefined) {
            return LocalSetting.logging;
        }
        const logging = localStorage.getItem('justa.logging');
        // localStorge based on origin, ie  web site, so the setting on one site can't be seen for another site
        // every site has at least 5mb size
        if (logging) {
            LocalSetting.logging = logging.toLowerCase() === 'true';
        }
        else {
            LocalSetting.logging = false;
        }
        return LocalSetting.logging;
    }
}


/***/ }),

/***/ "./src/app/fire/FormControlWithError.ts":
/*!**********************************************!*\
  !*** ./src/app/fire/FormControlWithError.ts ***!
  \**********************************************/
/*! exports provided: FormControlWithError */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "FormControlWithError", function() { return FormControlWithError; });
/* harmony import */ var _angular_forms__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/forms */ "../../node_modules/@angular/forms/__ivy_ngcc__/fesm2015/forms.js");

// please also see https://medium.com/@srik913/angular-validation-error-messages-display-using-directives-415a7616caf3
// for directive way to handle error message
class FormControlWithError extends _angular_forms__WEBPACK_IMPORTED_MODULE_0__["FormControl"] {
    getError() {
        if (this.invalid) {
            const keys = Object.keys(this.errors);
            for (const errKey of keys) {
                // only return first error
                // it is return error key now, better to return more meanful message
                return errKey;
            }
            return 'unknow error';
        }
        return '';
    }
    getErrorClass() {
        if (this.invalid) {
            return 'validation-error';
        }
        return '';
    }
}


/***/ }),

/***/ "./src/app/fire/justa-fire.component.ts":
/*!**********************************************!*\
  !*** ./src/app/fire/justa-fire.component.ts ***!
  \**********************************************/
/*! exports provided: JustaFireComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "JustaFireComponent", function() { return JustaFireComponent; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "../../node_modules/@angular/core/__ivy_ngcc__/fesm2015/core.js");
/* harmony import */ var _angular_forms__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/forms */ "../../node_modules/@angular/forms/__ivy_ngcc__/fesm2015/forms.js");
/* harmony import */ var _validator_commaNumberValidator__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ../validator/commaNumberValidator */ "./src/app/validator/commaNumberValidator.ts");
/* harmony import */ var rxjs_operators__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! rxjs/operators */ "../../node_modules/rxjs/_esm2015/operators/index.js");
/* harmony import */ var _FormControlWithError__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! ./FormControlWithError */ "./src/app/fire/FormControlWithError.ts");
/* harmony import */ var _validators__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(/*! ./validators */ "./src/app/fire/validators.ts");
/* harmony import */ var _common_LocalSetting__WEBPACK_IMPORTED_MODULE_6__ = __webpack_require__(/*! ../common/LocalSetting */ "./src/app/common/LocalSetting.ts");
/* harmony import */ var _angular_common__WEBPACK_IMPORTED_MODULE_7__ = __webpack_require__(/*! @angular/common */ "../../node_modules/@angular/common/__ivy_ngcc__/fesm2015/common.js");










function JustaFireComponent_div_15_Template(rf, ctx) { if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵelementStart"](0, "div");
    _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵelementStart"](1, "div");
    _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵtext"](2, "Java Experience:");
    _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵelementEnd"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵelement"](3, "input", 8);
    _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵelementEnd"]();
} if (rf & 2) {
    const cc_r3 = ctx.ngIf;
    _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵadvance"](3);
    _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵproperty"]("formControl", cc_r3)("ngClass", cc_r3.getErrorClass())("title", cc_r3.getError());
} }
function JustaFireComponent_div_20_Template(rf, ctx) { if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵelementStart"](0, "div");
    _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵelementStart"](1, "div");
    _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵtext"](2, "Range start:");
    _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵelementEnd"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵelement"](3, "input", 8);
    _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵelementEnd"]();
} if (rf & 2) {
    const cc_r4 = ctx.ngIf;
    _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵadvance"](3);
    _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵproperty"]("formControl", cc_r4)("ngClass", cc_r4.getErrorClass())("title", cc_r4.getError());
} }
function JustaFireComponent_div_21_Template(rf, ctx) { if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵelementStart"](0, "div");
    _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵelementStart"](1, "div");
    _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵtext"](2, "Range end:");
    _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵelementEnd"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵelement"](3, "input", 8);
    _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵelementEnd"]();
} if (rf & 2) {
    const cc_r5 = ctx.ngIf;
    _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵadvance"](3);
    _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵproperty"]("formControl", cc_r5)("ngClass", cc_r5.getErrorClass())("title", cc_r5.getError());
} }
const _c0 = function (a0) { return { "validation-error": a0 }; };
class JustaFireComponent {
    // myform = new FormGroup({
    //     myName: this.myName,
    //     mySalary: this.mySalary,
    //     myJavaExperience: this.myJavaExperience,
    //     rangeStart: this.rangeStart,
    //     rangeEnd: this.rangeEnd
    // });
    constructor(fb) {
        this.fb = fb;
        this.logging = false;
        this.color = 'red';
        this.myName = new _angular_forms__WEBPACK_IMPORTED_MODULE_1__["FormControl"]('Justin');
        this.mySalary2 = new _FormControlWithError__WEBPACK_IMPORTED_MODULE_4__["FormControlWithError"]('3,234,000.00');
        // put  on component directly help:
        //      IDE to do code completion suggesion even on html template
        //      Angular to do compiling checking
        //      Jasmine to do unit testing
        // if it is in myform, then those benefits are gone
        this.myJavaExperience = new _FormControlWithError__WEBPACK_IMPORTED_MODULE_4__["FormControlWithError"]('30.5');
        this.rangeStart = new _FormControlWithError__WEBPACK_IMPORTED_MODULE_4__["FormControlWithError"]('11');
        this.rangeEnd = new _FormControlWithError__WEBPACK_IMPORTED_MODULE_4__["FormControlWithError"]('32');
        this.logging = _common_LocalSetting__WEBPACK_IMPORTED_MODULE_6__["LocalSetting"].getLoggin();
        this.myName.setValidators(_angular_forms__WEBPACK_IMPORTED_MODULE_1__["Validators"].required);
        this.mySalary2.setValidators([
            _angular_forms__WEBPACK_IMPORTED_MODULE_1__["Validators"].required,
            Object(_validator_commaNumberValidator__WEBPACK_IMPORTED_MODULE_2__["CommaNumberValidator"])()
        ]);
        this.myJavaExperience.setValidators([
            _angular_forms__WEBPACK_IMPORTED_MODULE_1__["Validators"].required,
            Object(_validator_commaNumberValidator__WEBPACK_IMPORTED_MODULE_2__["CommaNumberValidator"])()
        ]);
        this.rangeStart.setValidators([
            _angular_forms__WEBPACK_IMPORTED_MODULE_1__["Validators"].required,
            _validators__WEBPACK_IMPORTED_MODULE_5__["MyAwesomeRangeValidator"]
        ]);
        this.rangeEnd.setValidators([
            _angular_forms__WEBPACK_IMPORTED_MODULE_1__["Validators"].required,
            _validators__WEBPACK_IMPORTED_MODULE_5__["MyAwesomeRangeValidator"]
        ]);
        this.myform = this.fb.group({
            myName: this.myName,
            mySalary: this.mySalary2,
            myJavaExperience: this.myJavaExperience,
            rangeStart: this.rangeStart,
            rangeEnd: this.rangeEnd
        });
        this.checkField();
        this.hookObserver();
    }
    ngOnInit() {
    }
    ngAfterViewInit() {
        this.mySalary2.patchValue('2345.5');
    }
    checkField() {
        if (!this.field) {
            this.errorMsg = 'Need to set field.';
            return false;
        }
        return true;
    }
    changeSalary() {
        this.mySalary2.patchValue('2345.5');
        // patch value always trigger the value change event even it is the same value as before
    }
    onDataLoading() {
        if (this.logging) {
            console.log('onJustaColorChange: reset color...');
        }
        if (!this.checkTargetField()) {
            return;
        }
        this.color = this.targetElement.value;
        this.errorMsg = '';
    }
    checkTargetField() {
        if (!this.checkField()) {
            return false;
        }
        this.targetElement = document.getElementById(this.field);
        if (!this.targetElement) {
            this.errorMsg = 'Didn\'t find target element: ' + this.field;
            return false;
        }
        return true;
    }
    hookObserver() {
        this.myform.valueChanges.pipe(Object(rxjs_operators__WEBPACK_IMPORTED_MODULE_3__["distinctUntilChanged"])())
            .subscribe(term => {
            if (this.myform.valid) {
                if (this.logging) {
                    console.log('valid data');
                }
            }
            else {
                if (this.logging) {
                    console.log('invalid data');
                }
            }
        });
    }
}
JustaFireComponent.ɵfac = function JustaFireComponent_Factory(t) { return new (t || JustaFireComponent)(_angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵdirectiveInject"](_angular_forms__WEBPACK_IMPORTED_MODULE_1__["FormBuilder"])); };
JustaFireComponent.ɵcmp = _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵdefineComponent"]({ type: JustaFireComponent, selectors: [["app-justa-fire"]], hostBindings: function JustaFireComponent_HostBindings(rf, ctx) { if (rf & 1) {
        _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵlistener"]("onJustaColorChange", function JustaFireComponent_onJustaColorChange_HostBindingHandler() { return ctx.onDataLoading(); }, false, _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵresolveWindow"]);
    } }, inputs: { field: "field" }, decls: 25, vars: 19, consts: [[1, "fire"], [2, "color", "red"], ["width", "24", "height", "24", "viewBox", "0 0 24 24", 2, "fill", "none"], ["d", "M13.5.67s.74 2.65.74 4.8c0 2.06-1.35 3.73-3.41 3.73-2.07 0-3.63-1.67-3.63-3.73l.03-.36C5.21 7.51 4 10.62 4 14c0 4.42 3.58 8 8 8s8-3.58 8-8C20 8.61 17.41 3.8 13.5.67zM11.71 19c-1.78 0-3.22-1.4-3.22-3.14 0-1.62 1.05-2.76 2.81-3.12 1.77-.36 3.6-1.21 4.62-2.58.39 1.29.59 2.65.59 4.04 0 2.65-2.15 4.8-4.8 4.8z"], [1, "myform"], ["novalidate", "", 3, "formGroup"], ["type", "text", 3, "ngClass", "title", "formControl"], [4, "ngIf"], ["type", "text", 3, "formControl", "ngClass", "title"], [3, "click"]], template: function JustaFireComponent_Template(rf, ctx) { if (rf & 1) {
        _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵelementStart"](0, "div", 0);
        _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵelementStart"](1, "label", 1);
        _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵtext"](2);
        _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵelementEnd"]();
        _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵelementStart"](3, "label");
        _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵtext"](4, " My fire ");
        _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵnamespaceSVG"]();
        _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵelementStart"](5, "svg", 2);
        _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵelement"](6, "path", 3);
        _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵelementEnd"]();
        _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵtext"](7, " works! ");
        _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵelementEnd"]();
        _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵnamespaceHTML"]();
        _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵelementStart"](8, "div", 4);
        _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵelementStart"](9, "form", 5);
        _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵtext"](10);
        _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵelementStart"](11, "div");
        _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵelementStart"](12, "div");
        _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵtext"](13, "Name: ");
        _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵelementEnd"]();
        _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵelement"](14, "input", 6);
        _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵelementEnd"]();
        _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵtemplate"](15, JustaFireComponent_div_15_Template, 4, 3, "div", 7);
        _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵelementStart"](16, "div");
        _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵelementStart"](17, "div");
        _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵtext"](18, "Salary:");
        _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵelementEnd"]();
        _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵelement"](19, "input", 8);
        _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵelementEnd"]();
        _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵtemplate"](20, JustaFireComponent_div_20_Template, 4, 3, "div", 7);
        _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵtemplate"](21, JustaFireComponent_div_21_Template, 4, 3, "div", 7);
        _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵelementStart"](22, "div");
        _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵelementStart"](23, "button", 9);
        _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵlistener"]("click", function JustaFireComponent_Template_button_click_23_listener() { return ctx.changeSalary(); });
        _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵtext"](24, "change salary");
        _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵelementEnd"]();
        _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵelementEnd"]();
        _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵelementEnd"]();
        _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵelementEnd"]();
        _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵelementEnd"]();
    } if (rf & 2) {
        _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵadvance"](1);
        _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵstyleProp"]("display", ctx.errorMsg ? "inline" : "none");
        _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵadvance"](1);
        _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵtextInterpolate"](ctx.errorMsg);
        _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵadvance"](3);
        _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵstyleProp"]("stroke", ctx.color);
        _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵadvance"](4);
        _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵproperty"]("formGroup", ctx.myform);
        _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵadvance"](1);
        _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵtextInterpolate1"](" Valid:", ctx.myform.valid, " ");
        _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵadvance"](4);
        _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵproperty"]("ngClass", _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵpureFunction1"](17, _c0, ctx.myName.invalid))("title", ctx.myName.invalid ? "required" : "")("formControl", ctx.myName);
        _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵadvance"](1);
        _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵproperty"]("ngIf", ctx.myJavaExperience);
        _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵadvance"](4);
        _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵproperty"]("formControl", ctx.mySalary2)("ngClass", ctx.mySalary2.getErrorClass())("title", ctx.mySalary2.getError());
        _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵadvance"](1);
        _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵproperty"]("ngIf", ctx.rangeStart);
        _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵadvance"](1);
        _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵproperty"]("ngIf", ctx.rangeEnd);
    } }, directives: [_angular_forms__WEBPACK_IMPORTED_MODULE_1__["ɵangular_packages_forms_forms_y"], _angular_forms__WEBPACK_IMPORTED_MODULE_1__["NgControlStatusGroup"], _angular_forms__WEBPACK_IMPORTED_MODULE_1__["FormGroupDirective"], _angular_forms__WEBPACK_IMPORTED_MODULE_1__["DefaultValueAccessor"], _angular_common__WEBPACK_IMPORTED_MODULE_7__["NgClass"], _angular_forms__WEBPACK_IMPORTED_MODULE_1__["NgControlStatus"], _angular_forms__WEBPACK_IMPORTED_MODULE_1__["FormControlDirective"], _angular_common__WEBPACK_IMPORTED_MODULE_7__["NgIf"]], styles: ["div.myform input.validation-error {\n  border: 1px dashed #a00 !important;\n  background-color: #ffefef !important;\n  box-shadow: none !important;\n}\n\ndiv.fire {\n  border: thin solid lightblue;\n  background-color: aquamarine;\n}\n\ndiv.myform div div {\n  width: 120px;\n  display: inline-block;\n}\n/*# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbInByb2plY3RzL2NvbXAyL3NyYy9hcHAvZmlyZS9DOlxcc2FtcGxlc1xcYW5ndWxhclxcYW5ndWxhci1oZWxsb1xcbXVsdGlwbGUvcHJvamVjdHNcXGNvbXAyXFxzcmNcXGFwcFxcZmlyZVxcanVzdGEtZmlyZS5jb21wb25lbnQuc2NzcyIsInByb2plY3RzL2NvbXAyL3NyYy9hcHAvZmlyZS9qdXN0YS1maXJlLmNvbXBvbmVudC5zY3NzIiwicHJvamVjdHMvY29tcDIvc3JjL2FwcC9maXJlL0M6XFxzYW1wbGVzXFxhbmd1bGFyXFxhbmd1bGFyLWhlbGxvXFxtdWx0aXBsZS9wcm9qZWN0c1xcY29tcDJcXHNyY1xcX3ZhcmlhYmxlcy5zY3NzIl0sIm5hbWVzIjpbXSwibWFwcGluZ3MiOiJBQUVBO0VBQ0ksa0NBQUE7RUFDQSxvQ0FBQTtFQUNBLDJCQUFBO0FDREo7O0FESUE7RUFDSSw0QkFBQTtFQUNBLDRCRVZTO0FEU2I7O0FER0E7RUFDSSxZQUFBO0VBQ0EscUJBQUE7QUNBSiIsImZpbGUiOiJwcm9qZWN0cy9jb21wMi9zcmMvYXBwL2ZpcmUvanVzdGEtZmlyZS5jb21wb25lbnQuc2NzcyIsInNvdXJjZXNDb250ZW50IjpbIkBpbXBvcnQgXCIuLi8uLi9fdmFyaWFibGVzLnNjc3NcIjtcclxuXHJcbmRpdi5teWZvcm0gaW5wdXQudmFsaWRhdGlvbi1lcnJvcntcclxuICAgIGJvcmRlcjogMXB4IGRhc2hlZCAjYTAwICFpbXBvcnRhbnQ7XHJcbiAgICBiYWNrZ3JvdW5kLWNvbG9yOiAjZmZlZmVmICFpbXBvcnRhbnQ7XHJcbiAgICBib3gtc2hhZG93OiBub25lICFpbXBvcnRhbnQ7ICAgICAgICAgICAgICAgIFxyXG59XHJcblxyXG5kaXYuZmlyZXtcclxuICAgIGJvcmRlcjp0aGluIHNvbGlkIGxpZ2h0Ymx1ZTtcclxuICAgIGJhY2tncm91bmQtY29sb3I6JGJrLWNvbG9yLWE7XHJcbn1cclxuZGl2Lm15Zm9ybSBkaXYgZGl2e1xyXG4gICAgd2lkdGg6MTIwcHg7XHJcbiAgICBkaXNwbGF5OiBpbmxpbmUtYmxvY2s7IFxyXG59IiwiZGl2Lm15Zm9ybSBpbnB1dC52YWxpZGF0aW9uLWVycm9yIHtcbiAgYm9yZGVyOiAxcHggZGFzaGVkICNhMDAgIWltcG9ydGFudDtcbiAgYmFja2dyb3VuZC1jb2xvcjogI2ZmZWZlZiAhaW1wb3J0YW50O1xuICBib3gtc2hhZG93OiBub25lICFpbXBvcnRhbnQ7XG59XG5cbmRpdi5maXJlIHtcbiAgYm9yZGVyOiB0aGluIHNvbGlkIGxpZ2h0Ymx1ZTtcbiAgYmFja2dyb3VuZC1jb2xvcjogYXF1YW1hcmluZTtcbn1cblxuZGl2Lm15Zm9ybSBkaXYgZGl2IHtcbiAgd2lkdGg6IDEyMHB4O1xuICBkaXNwbGF5OiBpbmxpbmUtYmxvY2s7XG59IiwiJGJrLWNvbG9yLWE6IGFxdWFtYXJpbmU7Il19 */"], encapsulation: 3 });
/*@__PURE__*/ (function () { _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵsetClassMetadata"](JustaFireComponent, [{
        type: _angular_core__WEBPACK_IMPORTED_MODULE_0__["Component"],
        args: [{
                selector: 'app-justa-fire',
                templateUrl: './justa-fire.component.html',
                styleUrls: ['./justa-fire.component.scss'],
                encapsulation: _angular_core__WEBPACK_IMPORTED_MODULE_0__["ViewEncapsulation"].ShadowDom
            }]
    }], function () { return [{ type: _angular_forms__WEBPACK_IMPORTED_MODULE_1__["FormBuilder"] }]; }, { field: [{
            type: _angular_core__WEBPACK_IMPORTED_MODULE_0__["Input"]
        }], onDataLoading: [{
            type: _angular_core__WEBPACK_IMPORTED_MODULE_0__["HostListener"],
            args: ['window:onJustaColorChange']
        }] }); })();


/***/ }),

/***/ "./src/app/fire/validators.ts":
/*!************************************!*\
  !*** ./src/app/fire/validators.ts ***!
  \************************************/
/*! exports provided: MyAwesomeRangeValidator */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "MyAwesomeRangeValidator", function() { return MyAwesomeRangeValidator; });
// corsss multipel fields validation sample
// comes from https://medium.com/@realTomaszKula/angular-cross-field-validation-d94e0d063b61
// please also see https://dzone.com/articles/how-to-do-conditional-validation-on-valuechanges-m
// for another way
const MyAwesomeRangeValidator = (fc) => {
    const start = fc.parent.get('rangeStart').value;
    const end = fc.parent.get('rangeEnd').value;
    if (start === '' || end === '') {
        return null;
    }
    const x = Number(start);
    const y = Number(end);
    if (x < y) {
        return null;
    }
    else {
        return { rangeIsIncorrect: true };
    }
};


/***/ }),

/***/ "./src/app/multi-user-picker/multi-user-picker.component.ts":
/*!******************************************************************!*\
  !*** ./src/app/multi-user-picker/multi-user-picker.component.ts ***!
  \******************************************************************/
/*! exports provided: MultiUserPickerComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "MultiUserPickerComponent", function() { return MultiUserPickerComponent; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "../../node_modules/@angular/core/__ivy_ngcc__/fesm2015/core.js");
/* harmony import */ var _common_LocalSetting__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ../common/LocalSetting */ "./src/app/common/LocalSetting.ts");
/* harmony import */ var _environments_environment__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ../../environments/environment */ "./src/environments/environment.ts");
/* harmony import */ var _angular_common_http__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! @angular/common/http */ "../../node_modules/@angular/common/__ivy_ngcc__/fesm2015/http.js");
/* harmony import */ var _angular_common__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! @angular/common */ "../../node_modules/@angular/common/__ivy_ngcc__/fesm2015/common.js");
/* harmony import */ var _angular_forms__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(/*! @angular/forms */ "../../node_modules/@angular/forms/__ivy_ngcc__/fesm2015/forms.js");







function MultiUserPickerComponent_option_6_Template(rf, ctx) { if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵelementStart"](0, "option", 5);
    _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵtext"](1);
    _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵelementEnd"]();
} if (rf & 2) {
    const x_r1 = ctx.$implicit;
    _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵproperty"]("value", x_r1.id);
    _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵadvance"](1);
    _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵtextInterpolate"](x_r1.name);
} }
class MultiUserPickerComponent {
    constructor(http) {
        this.http = http;
        this.logging = false;
        this.loading = false;
        this.selected = '';
        this.list = [];
        this.logging = _common_LocalSetting__WEBPACK_IMPORTED_MODULE_1__["LocalSetting"].getLoggin();
        if (_environments_environment__WEBPACK_IMPORTED_MODULE_2__["environment"].localApi) {
            this.apiUrl = 'assets/user.list.json';
            // this.apiUrl = 'assets/user.api.wrong.field.json';
            // this.apiUrl = 'assets/user.api.not.array.json';
        }
        else {
            this.apiUrl = '/jersey2/api/users/range.json?from=1&to=100000';
        }
        if (this.logging) {
            console.log(this.apiUrl);
        }
        this.loading = true;
        this.http.get(this.apiUrl).subscribe((res) => {
            this.loading = false;
            this.handleApiResonse(res);
        }, (err) => {
            this.loading = false;
            this.errorMsg = `API Error: ${err.status} ${err.statusText}`;
        });
    }
    ngOnInit() {
    }
    handleApiResonse(res) {
        if (this.logging) {
            console.log('handle API response...');
        }
        const tempList = [];
        // convert to target option list
        this.errorMsg = '';
        if (!Array.isArray(res)) {
            this.errorMsg = 'Didn\'t find array from API response';
            return;
        }
        res.forEach(item => {
            if (!item.id) {
                this.errorMsg = 'Didn\'t find id from API response';
                return;
            }
            if (!item.name) {
                this.errorMsg = 'Didn\'t find name from API response';
                return;
            }
            const one = {
                id: item.id,
                name: item.name
            };
            tempList.push(one);
        });
        if (this.errorMsg) {
            return;
        }
        this.list = tempList;
        // set selected
    }
    handleChange(x) {
        const ele = x.srcElement;
        let selected = '';
        for (let i = 0; i < ele.options.length; i++) {
            const o = ele.options[i];
            if (o.selected) {
                if (selected) {
                    selected = selected + '; ' + o.text;
                }
                else {
                    selected = o.text;
                }
            }
        }
        this.selected = selected;
        // const selectedUserList = ele.options.map(function (o) {
        //     return o.text;
        // }).filter(function (o) {
        //     return o.selected;
        // });
        //console.log(selectedUserList);
    }
}
MultiUserPickerComponent.ɵfac = function MultiUserPickerComponent_Factory(t) { return new (t || MultiUserPickerComponent)(_angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵdirectiveInject"](_angular_common_http__WEBPACK_IMPORTED_MODULE_3__["HttpClient"])); };
MultiUserPickerComponent.ɵcmp = _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵdefineComponent"]({ type: MultiUserPickerComponent, selectors: [["app-multi-user-picker"]], decls: 8, vars: 5, consts: [[1, "user-picker"], [1, "color_F"], [1, "spinSmIcon"], ["multiple", "", 2, "width", "210px", "height", "140px", 3, "change"], [3, "value", 4, "ngFor", "ngForOf"], [3, "value"]], template: function MultiUserPickerComponent_Template(rf, ctx) { if (rf & 1) {
        _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵelementStart"](0, "div", 0);
        _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵelementStart"](1, "span", 1);
        _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵtext"](2);
        _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵelementEnd"]();
        _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵelement"](3, "span", 2);
        _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵelement"](4, "br");
        _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵelementStart"](5, "select", 3);
        _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵlistener"]("change", function MultiUserPickerComponent_Template_select_change_5_listener($event) { return ctx.handleChange($event); });
        _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵtemplate"](6, MultiUserPickerComponent_option_6_Template, 2, 2, "option", 4);
        _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵelementEnd"]();
        _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵtext"](7);
        _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵelementEnd"]();
    } if (rf & 2) {
        _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵadvance"](2);
        _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵtextInterpolate"](ctx.errorMsg);
        _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵadvance"](1);
        _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵstyleProp"]("display", ctx.loading ? "inline-block" : "none");
        _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵadvance"](3);
        _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵproperty"]("ngForOf", ctx.list);
        _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵadvance"](1);
        _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵtextInterpolate1"](" Selcted: ", ctx.selected, "\n");
    } }, directives: [_angular_common__WEBPACK_IMPORTED_MODULE_4__["NgForOf"], _angular_forms__WEBPACK_IMPORTED_MODULE_5__["NgSelectOption"], _angular_forms__WEBPACK_IMPORTED_MODULE_5__["ɵangular_packages_forms_forms_x"]], styles: ["div.user-picker[_ngcontent-%COMP%] {\n  background-color: aquamarine;\n  border: thin solid lightcoral;\n}\n/*# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbInByb2plY3RzL2NvbXAyL3NyYy9hcHAvbXVsdGktdXNlci1waWNrZXIvQzpcXHNhbXBsZXNcXGFuZ3VsYXJcXGFuZ3VsYXItaGVsbG9cXG11bHRpcGxlL3Byb2plY3RzXFxjb21wMlxcc3JjXFxhcHBcXG11bHRpLXVzZXItcGlja2VyXFxtdWx0aS11c2VyLXBpY2tlci5jb21wb25lbnQuc2NzcyIsInByb2plY3RzL2NvbXAyL3NyYy9hcHAvbXVsdGktdXNlci1waWNrZXIvQzpcXHNhbXBsZXNcXGFuZ3VsYXJcXGFuZ3VsYXItaGVsbG9cXG11bHRpcGxlL3Byb2plY3RzXFxjb21wMlxcc3JjXFxfdmFyaWFibGVzLnNjc3MiLCJwcm9qZWN0cy9jb21wMi9zcmMvYXBwL211bHRpLXVzZXItcGlja2VyL211bHRpLXVzZXItcGlja2VyLmNvbXBvbmVudC5zY3NzIl0sIm5hbWVzIjpbXSwibWFwcGluZ3MiOiJBQUNBO0VBQ0ksNEJDRlM7RURJVCw2QkFBQTtBRURKIiwiZmlsZSI6InByb2plY3RzL2NvbXAyL3NyYy9hcHAvbXVsdGktdXNlci1waWNrZXIvbXVsdGktdXNlci1waWNrZXIuY29tcG9uZW50LnNjc3MiLCJzb3VyY2VzQ29udGVudCI6WyJAaW1wb3J0IFwiLi4vLi4vX3ZhcmlhYmxlcy5zY3NzXCI7XHJcbmRpdi51c2VyLXBpY2tlcntcclxuICAgIGJhY2tncm91bmQtY29sb3I6JGJrLWNvbG9yLWE7XHJcblxyXG4gICAgYm9yZGVyOnRoaW4gc29saWQgbGlnaHRjb3JhbFxyXG59IiwiJGJrLWNvbG9yLWE6IGFxdWFtYXJpbmU7IiwiZGl2LnVzZXItcGlja2VyIHtcbiAgYmFja2dyb3VuZC1jb2xvcjogYXF1YW1hcmluZTtcbiAgYm9yZGVyOiB0aGluIHNvbGlkIGxpZ2h0Y29yYWw7XG59Il19 */"] });
/*@__PURE__*/ (function () { _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵsetClassMetadata"](MultiUserPickerComponent, [{
        type: _angular_core__WEBPACK_IMPORTED_MODULE_0__["Component"],
        args: [{
                selector: 'app-multi-user-picker',
                templateUrl: './multi-user-picker.component.html',
                styleUrls: ['./multi-user-picker.component.scss']
            }]
    }], function () { return [{ type: _angular_common_http__WEBPACK_IMPORTED_MODULE_3__["HttpClient"] }]; }, null); })();


/***/ }),

/***/ "./src/app/profit-dropdown/profit-dropdown.component.ts":
/*!**************************************************************!*\
  !*** ./src/app/profit-dropdown/profit-dropdown.component.ts ***!
  \**************************************************************/
/*! exports provided: ProfitDropdownComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "ProfitDropdownComponent", function() { return ProfitDropdownComponent; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "../../node_modules/@angular/core/__ivy_ngcc__/fesm2015/core.js");
/* harmony import */ var projects_shared_src_public_api__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! projects/shared/src/public-api */ "../shared/src/public-api.ts");
/* harmony import */ var _environments_environment__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ../../environments/environment */ "./src/environments/environment.ts");
/* harmony import */ var _angular_common__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! @angular/common */ "../../node_modules/@angular/common/__ivy_ngcc__/fesm2015/common.js");
/* harmony import */ var _angular_forms__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! @angular/forms */ "../../node_modules/@angular/forms/__ivy_ngcc__/fesm2015/forms.js");






function ProfitDropdownComponent_option_4_Template(rf, ctx) { if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵelementStart"](0, "option", 2);
    _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵtext"](1);
    _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵelementEnd"]();
} if (rf & 2) {
    const x_r1 = ctx.$implicit;
    _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵproperty"]("value", x_r1.value)("selected", x_r1.selected);
    _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵadvance"](1);
    _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵtextInterpolate"](x_r1.text);
} }
class ProfitDropdownComponent {
    constructor() {
        this.list = [];
        this.handleProfit(projects_shared_src_public_api__WEBPACK_IMPORTED_MODULE_1__["ProfitType"].Computer);
        this.handleDirection(projects_shared_src_public_api__WEBPACK_IMPORTED_MODULE_1__["OProfitType"].Computer);
    }
    ngOnInit() {
        console.log(_environments_environment__WEBPACK_IMPORTED_MODULE_2__["environment"].production);
    }
    handleProfit(p) {
        let x = projects_shared_src_public_api__WEBPACK_IMPORTED_MODULE_1__["ProfitType"][1];
        console.log(x); // return 'SmartPhone'
        x = projects_shared_src_public_api__WEBPACK_IMPORTED_MODULE_1__["ProfitType"][9];
        console.log(x); // undefined
        for (const pt in projects_shared_src_public_api__WEBPACK_IMPORTED_MODULE_1__["ProfitType"]) {
            if (isNaN(Number(pt))) {
                console.log(pt);
            }
        }
    }
    handleDirection(d) {
        console.log(d);
        console.log(projects_shared_src_public_api__WEBPACK_IMPORTED_MODULE_1__["OProfitType"][d]); // didn't work, why?
        for (const od in projects_shared_src_public_api__WEBPACK_IMPORTED_MODULE_1__["OProfitType"]) {
            if (od) {
                const one = {
                    value: projects_shared_src_public_api__WEBPACK_IMPORTED_MODULE_1__["OProfitType"][od],
                    text: od,
                    selected: false
                };
                this.list.push(one);
            }
        }
    }
}
ProfitDropdownComponent.ɵfac = function ProfitDropdownComponent_Factory(t) { return new (t || ProfitDropdownComponent)(); };
ProfitDropdownComponent.ɵcmp = _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵdefineComponent"]({ type: ProfitDropdownComponent, selectors: [["app-profit-dropdown"]], decls: 5, vars: 1, consts: [[1, "profit-div"], [3, "value", "selected", 4, "ngFor", "ngForOf"], [3, "value", "selected"]], template: function ProfitDropdownComponent_Template(rf, ctx) { if (rf & 1) {
        _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵelementStart"](0, "div", 0);
        _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵelementStart"](1, "label");
        _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵtext"](2, "This is a web component: ");
        _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵelementEnd"]();
        _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵelementStart"](3, "select");
        _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵtemplate"](4, ProfitDropdownComponent_option_4_Template, 2, 3, "option", 1);
        _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵelementEnd"]();
        _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵelementEnd"]();
    } if (rf & 2) {
        _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵadvance"](4);
        _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵɵproperty"]("ngForOf", ctx.list);
    } }, directives: [_angular_common__WEBPACK_IMPORTED_MODULE_3__["NgForOf"], _angular_forms__WEBPACK_IMPORTED_MODULE_4__["NgSelectOption"], _angular_forms__WEBPACK_IMPORTED_MODULE_4__["ɵangular_packages_forms_forms_x"]], styles: ["div.profit-div[_ngcontent-%COMP%] {\n  background-color: aquamarine;\n}\n/*# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbInByb2plY3RzL2NvbXAyL3NyYy9hcHAvcHJvZml0LWRyb3Bkb3duL0M6XFxzYW1wbGVzXFxhbmd1bGFyXFxhbmd1bGFyLWhlbGxvXFxtdWx0aXBsZS9wcm9qZWN0c1xcY29tcDJcXHNyY1xcYXBwXFxwcm9maXQtZHJvcGRvd25cXHByb2ZpdC1kcm9wZG93bi5jb21wb25lbnQuc2NzcyIsInByb2plY3RzL2NvbXAyL3NyYy9hcHAvcHJvZml0LWRyb3Bkb3duL0M6XFxzYW1wbGVzXFxhbmd1bGFyXFxhbmd1bGFyLWhlbGxvXFxtdWx0aXBsZS9wcm9qZWN0c1xcY29tcDJcXHNyY1xcX3ZhcmlhYmxlcy5zY3NzIiwicHJvamVjdHMvY29tcDIvc3JjL2FwcC9wcm9maXQtZHJvcGRvd24vcHJvZml0LWRyb3Bkb3duLmNvbXBvbmVudC5zY3NzIl0sIm5hbWVzIjpbXSwibWFwcGluZ3MiOiJBQUVBO0VBQ0ksNEJDSFM7QUNFYiIsImZpbGUiOiJwcm9qZWN0cy9jb21wMi9zcmMvYXBwL3Byb2ZpdC1kcm9wZG93bi9wcm9maXQtZHJvcGRvd24uY29tcG9uZW50LnNjc3MiLCJzb3VyY2VzQ29udGVudCI6WyJAaW1wb3J0IFwiLi4vLi4vX3ZhcmlhYmxlcy5zY3NzXCI7XHJcblxyXG5kaXYucHJvZml0LWRpdntcclxuICAgIGJhY2tncm91bmQtY29sb3I6JGJrLWNvbG9yLWE7XHJcbn0iLCIkYmstY29sb3ItYTogYXF1YW1hcmluZTsiLCJkaXYucHJvZml0LWRpdiB7XG4gIGJhY2tncm91bmQtY29sb3I6IGFxdWFtYXJpbmU7XG59Il19 */"] });
/*@__PURE__*/ (function () { _angular_core__WEBPACK_IMPORTED_MODULE_0__["ɵsetClassMetadata"](ProfitDropdownComponent, [{
        type: _angular_core__WEBPACK_IMPORTED_MODULE_0__["Component"],
        args: [{
                selector: 'app-profit-dropdown',
                templateUrl: './profit-dropdown.component.html',
                styleUrls: ['./profit-dropdown.component.scss']
            }]
    }], function () { return []; }, null); })();


/***/ }),

/***/ "./src/app/validator/ValidTypeList.ts":
/*!********************************************!*\
  !*** ./src/app/validator/ValidTypeList.ts ***!
  \********************************************/
/*! exports provided: ValidTypeList */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "ValidTypeList", function() { return ValidTypeList; });
class ValidTypeList {
}
ValidTypeList.FloatComma = /^-?(?=.)(\d{1,3}(,\d{3})*)?(\.\d+)?$/; // for number 12,345,678.0045
// commas are required
// Can start with "."
// Can be negative
// Pass: 1,234,567.8912, 1234567.8912
// Fail: 10000,000, 1,00,00.34, -234,567.00
ValidTypeList.PositiveFloatOptionalComma = /^(\d+|\d{1,3}(,\d{3})*)(\.\d+)?$/;


/***/ }),

/***/ "./src/app/validator/commaNumberValidator.ts":
/*!***************************************************!*\
  !*** ./src/app/validator/commaNumberValidator.ts ***!
  \***************************************************/
/*! exports provided: CommaNumberValidator */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "CommaNumberValidator", function() { return CommaNumberValidator; });
/* harmony import */ var _ValidTypeList__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! ./ValidTypeList */ "./src/app/validator/ValidTypeList.ts");

const CommaNumber = _ValidTypeList__WEBPACK_IMPORTED_MODULE_0__["ValidTypeList"].PositiveFloatOptionalComma;
function CommaNumberValidator() {
    return (control) => {
        const isValid = CommaNumber.test(control.value);
        return isValid ? null : { IncorrectFormatNumber: { value: control.value } };
    };
}


/***/ }),

/***/ "./src/environments/environment.ts":
/*!*****************************************!*\
  !*** ./src/environments/environment.ts ***!
  \*****************************************/
/*! exports provided: environment */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "environment", function() { return environment; });
// This file can be replaced during build by using the `fileReplacements` array.
// `ng build --prod` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.
const environment = {
    production: false,
    localApi: true,
};
/*
 * For easier debugging in development mode, you can import the following file
 * to ignore zone related error stack frames such as `zone.run`, `zoneDelegate.invokeTask`.
 *
 * This import should be commented out in production mode because it will have a negative impact
 * on performance if an error is thrown.
 */
// import 'zone.js/dist/zone-error';  // Included with Angular CLI.


/***/ }),

/***/ "./src/main.ts":
/*!*********************!*\
  !*** ./src/main.ts ***!
  \*********************/
/*! no exports provided */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "../../node_modules/@angular/core/__ivy_ngcc__/fesm2015/core.js");
/* harmony import */ var _environments_environment__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ./environments/environment */ "./src/environments/environment.ts");
/* harmony import */ var _app_app_module__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ./app/app.module */ "./src/app/app.module.ts");
/* harmony import */ var _angular_platform_browser__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! @angular/platform-browser */ "../../node_modules/@angular/platform-browser/__ivy_ngcc__/fesm2015/platform-browser.js");




if (_environments_environment__WEBPACK_IMPORTED_MODULE_1__["environment"].production) {
    Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["enableProdMode"])();
}
_angular_platform_browser__WEBPACK_IMPORTED_MODULE_3__["platformBrowser"]().bootstrapModule(_app_app_module__WEBPACK_IMPORTED_MODULE_2__["AppModule"])
    .catch(err => console.error(err));


/***/ }),

/***/ 0:
/*!***************************!*\
  !*** multi ./src/main.ts ***!
  \***************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

module.exports = __webpack_require__(/*! C:\samples\angular\angular-hello\multiple\projects\comp2\src\main.ts */"./src/main.ts");


/***/ })

},[[0,"runtime","vendor"]]]);
//# sourceMappingURL=main-es2015.js.map