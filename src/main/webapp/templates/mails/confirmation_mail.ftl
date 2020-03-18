<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Sign Up</title>
</head>
<body>
<div>
    <table cellspacing="0" cellpadding="0" border="0"
           style="color:#333;background:#fff;padding:0;margin:0;width:100%;font:15px 'Helvetica Neue',Arial,Helvetica">
        <tbody>
        <tr width="100%">
            <td valign="top" align="left" style="background:#f0f0f0;font:15px 'Helvetica Neue',Arial,Helvetica">
                <table style="border:none;padding:0 18px;margin:50px auto;width:500px">
                    <tbody>
                    <tr width="100%" height="57">
                        <td valign="top" align="left"
                            style="border-top-left-radius:4px;border-top-right-radius:4px;background:#0079bf;padding:12px 18px;text-align:center">
                        </td>
                    </tr>
                    <tr width="100%">
                        <td valign="top" align="left"
                            style="border-bottom-left-radius:4px;border-bottom-right-radius:4px;background:#fff;padding:18px">
                            <h1 style="font-size:20px;margin:0;color:#333"> Dear ${firstName} ${lastName}, </h1>
                            <p style="font:15px/1.25em 'Helvetica Neue',Arial,Helvetica;color:#333">
                                Thanks for registration! To complete your sign up, please confirm your email address:
                                <a href="http://localhost:8080/confirm/${identificator}">${email}</a>.
                            </p>
                            <p style="font:15px/1.25em 'Helvetica Neue',Arial,Helvetica;color:#939393;margin-bottom:0">
                                Once verified, you can get full access to all our features.
                            </p>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </td>
        </tr>
        </tbody>
    </table>
</div>
</body>
</html>