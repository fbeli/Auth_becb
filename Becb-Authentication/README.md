docker run -p 3306:3306 --name some-mysql -v $(pwd)/mysql:/var/lib/mysql -e MYSQL_ROOT_PASSWORD=123 -d mysql:latest



Links 

    1 - Get access-token : auth.local:8082/oauth/token
    2 - ckeck access_token : auth.local:8082/oauth/check_token




Code Verifier: teste123
Code Challenger: teste123  // por estar usando o plain, usar o SHA256

http://auth.local:8082/oauth/authorize?response_type=code&client_id=xpto_grant&redirect_uri=http://aplicacao-cliente&code_challenge=teste123&code_challenge_method=plain

