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
