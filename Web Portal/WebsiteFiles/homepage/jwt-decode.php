<?php
require('./bootstrap.php');

// read a JWT from the command line
$jwt = "eyJraWQiOiJKUnlya2RVOUZ1ZHRJT2ZYMk5UbHd6SFJ1ejdMVlFcL2ZsUDZtS3liSVVBVT0iLCJhbGciOiJSUzI1NiJ9.eyJhdF9oYXNoIjoiWEJkVkZTd1ZXbFdrUGlwQWthOXRjdyIsInN1YiI6IjQxYmNiNjA2LWVjZTgtNDA2NC1hYzBiLTNmN2JjMmU3ZjdiNSIsImF1ZCI6IjUzOG8yOXQ3NGZnM20ydGs2MGNpYXNibXM0IiwiZW1haWxfdmVyaWZpZWQiOnRydWUsImV2ZW50X2lkIjoiNzk3Y2NjOWUtOTFkNi00ODFiLThhODQtZDYwOWE0YTQ1ZTRhIiwidG9rZW5fdXNlIjoiaWQiLCJhdXRoX3RpbWUiOjE1ODU0OTYyMTksImlzcyI6Imh0dHBzOlwvXC9jb2duaXRvLWlkcC51cy1lYXN0LTIuYW1hem9uYXdzLmNvbVwvdXMtZWFzdC0yXzZkVmVDc3loMiIsImNvZ25pdG86dXNlcm5hbWUiOiJzbWl0aDIyIiwiZXhwIjoxNTg1NDk5ODE5LCJpYXQiOjE1ODU0OTYyMTksImVtYWlsIjoic21pdDMwMTlAcHVyZHVlLmVkdSJ9.FY_61kP3Ah59gF0LkuP7dWVtayT7HVaY4854h3oUwVqCLi2JivZRP1cd6XvpPkxyne6ecTNl_irUWaSvUw3QBlLSyv8_eiiqP3XlmotoiKnHLDbDFQucGdAL7ZOaxDJDH2aLewjLb7du18ZaIthDBWPGxbbJuOa3g_WaYazHuuDBHeXhK1fxjmtqN5nXu2ZpqU4hgDczPFjkB5KoPRo95jQE1ZvQX8Kppxz3iFK058ZDkWXtufcajHsYF2R1MT8ko9MLu7lOjyfC7xGeBe-h4OJ75-PkM2ySJcBtvYhBHMsB96s5nKb6RIgIybYDspxfhnPBEPDuUFy9yqaB_LQjsw";
list($header, $payload, $signature) = explode(".", $jwt);

$plainHeader = base64_decode($header);
echo "Header:\n$plainHeader\n\n";

$plainPayload = base64_decode($payload);
echo "Payload:\n$plainPayload\n\n";
