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
    winHttp.Open "GET", "http://CA09417D:8380/spnego/JWTToken"
    winHttp.send
    a = winHttp.responseText
    MsgBox a
    
End Sub
```
