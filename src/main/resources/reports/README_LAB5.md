# Topic: Hash functions and Digital Signatures.
### Course: Cryptography & Security
### Author: Babcinetchi Egor

----
## Objectives:
   1. Take what you have at the moment from previous laboratory works and put it in a web service / serveral web services.
   2. Your services should have implemented basic authentication and MFA (the authentication factors of your choice).
   3. Your web app needs to simulate user authorization and the way you authorise user is also a choice that needs to be done by you.
   4. As services that your application could provide, you could use the classical ciphers. Basically the user would like to get access and use the classical ciphers, but they need to authenticate and be authorized.
## Implementation
The project is a web server composed of 3 classes: ```Authenticator```, ```CipherAccessService``` and ```UserServiceImpl```.
<br>

* ```Authenticator``` is responsible for all actions related to authentication and authorization. It registers and verifies 
user, creates secret keys for each new user, hashes and saves passwords in db. It generates secret key and qrcode and accesses 
Google Authenticator for MF Authentication.
<br>

* ```CipherAccessService``` is a web server. It listens for request, encrypts/decrypts and authenticates/authorizes users.
<br>

* ```UserServiceImpl``` performs basic CRUD operations on users.
### Web server
```CipherAccessService``` servers as a web service. It inits and starts ```HttpServer``` to listen for incoming requests.
<br>
At the moment there are 4 available endpoints: ```/encrypt```, ```/decrypt```, ```/register```, ```/login```.
<br>
```/encrypt``` and ```/decrypt``` are self-explanatory. 
![Screenshot from 2022-12-15 23-59-21.png](..%2Fassets%2Fciphers%2Fauthenticator%2FScreenshot%20from%202022-12-15%2023-59-21.png)
```/register``` is a POST request that takes ```UserDto``` object 
as an entity for user. This object includes fields as name, surname, email, password, code and role. This endpoint accesses
```
public void register(UserDto userDto) {...}
```
![Screenshot from 2022-12-15 22-39-01.png](..%2Fassets%2Fciphers%2Fauthenticator%2FScreenshot%20from%202022-12-15%2022-39-01.png)

```/login``` is meant to verify a user during authentication and authorization. It accesses 
``` 
    public boolean verify(UserDto userDto, String code) {...}
```
### Authenticator
As written previously, ```Authenticator``` performs all authentication and authorization related actions. 
More precisely it registers users, generates unique keys, QR codes for registration in Google Authenticator. It performs 
authentication and authorization of users as well.
<br>
#### Registration
``` public void register(UserDto userDto) ``` method performs following actions:
1. Generates unique secret key used to generate Google Authenticator
   ```String secret = generateSecretKey();```
2. Saves this key into Tokens DB for further using during authentication
   ``` TokenRepository.getInstance().add(userDto.getEmail(), secret); ```
3. Hashes and saves password into Passwords DB
    ``` 
        DigitalSignature ds = new DigitalSignature();
        PasswordRepository.getInstance().add(userDto.getEmail(), ds.hash(userDto.getPassword()));
    ```
4. Generates QR code based on secret key
   ``` getUserQrCode(secret, userDto.getEmail()); ```
5. Saves users into User DB
   ``` userService.addUser(userDto); ```
   <br>

#### Authentication
``` public boolean verify(UserDto userDto, String code) {...} ```
method authenticates user. It verifies digital signature of password with hashed entry from Password DB and check
the entered code with Google Authenticator one. 
```        
    DigitalSignature ds = new DigitalSignature();
    return ds.verify(userDto.getEmail(), ds.create(userDto.getPassword()))
            && code.equals(getTOTPCode(TokenRepository.getInstance().get(userDto.getEmail())));
```
If code expires or password is incorrect, authentication fails.
![Screenshot from 2022-12-15 23-56-14.png](..%2Fassets%2Fciphers%2Fauthenticator%2FScreenshot%20from%202022-12-15%2023-56-14.png)
#### Authorization
```public boolean authorize(InputDto input)``` authorize user based on input ```cipherID```. CipherID is the identifier
of a cipher. Here it is just the name of the cipher. Authorization is based on checking whether cipher belongs to a role or 
not. All role-cipher mappings can be checked in ```Role``` enum. 
```         
return role.containsCipher(cipher);
```
![Screenshot from 2022-12-15 22-49-19.png](..%2Fassets%2Fciphers%2Fauthenticator%2FScreenshot%20from%202022-12-15%2022-49-19.png)

If role and cipher are not compatible, the authorization fails
![Screenshot from 2022-12-15 22-48-29.png](..%2Fassets%2Fciphers%2Fauthenticator%2FScreenshot%20from%202022-12-15%2022-48-29.png)
#### Secret key generation
In order to perform MFA, there was chosen Google Authenticator service. It is a third-party service using time-based one-time
password for user authentication. 
<br>
Google Authenticator requires two parameters to start generating passwords: account name and secret key/qr code.
These parameters are passed to Authenticator app. In order to verify input code with that one generated by Google Authenticator 
there is used external library TOTP.
<br>
In order to enhance user experience, there was added QR code generation based on required by Authenticator App parameters.
<br>
![code.png](..%2Fqrcodes%2Fcode.png)
<br>
This qr code is generated on user registration and is updated on each ```/register``` request.

## Conclusion
During Web Server development there were studied authentication and authorization techniques. After investigations and analyzing
possible solutions for requirements, there was started the development of the web server.
<br>
The final web server contains authentication and authorization services that are using MFA solutions (Google Authenticator).
<br>
The authentication process consists of password hashing and storing, secret key generation and MFA verification.
<br>
The authorization process is based on checking for available ciphers for each role.
<br>
Finally, there are integrated already developed ciphers with new solution to support encryption and decryption.