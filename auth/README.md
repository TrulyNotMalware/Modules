# Auth Application
The Auth application implements two commonly required features: **Login & Register** and **OAuth-client**. You can activate or deactivate the OAuth-client feature simply by including **"oauth-client"** in the profile name. Supports JWT authentication using RSA-256. And also offers the essential resources needed for deployment in a Kubernetes environment. This project depends on the [security module](https://github.com/TrulyNotMalware/Modules/blob/main/security/README.md), if you decide not to use this module you'll need to configure some extra components.

## Included
- RSA-256 Authentication.
- OAuth2 Client
    - GitHub (2023.9.15)

## Getting Started
### Configurations
First, As the security modules is in use, Refer to [README](https://github.com/TrulyNotMalware/Modules/blob/main/security/README.md#Configurations) and complete the *application.yaml*( JPA & Database setup and Jwt setups ). You'll also require a key file for the security module, and if you're using an Oracle database, a wallet may be required.
### OAuth Configurations
To use social login, you must register your application with OAuth provider. For this example, I've implmented only a few providers(may be added or update in the future). Keep in mind this process can differ between providers.
#### GitHub
You can easily create an OAuth App for GitHub using [this link](https://github.com/settings/developers) by writing YOUR_DOMAIN/login/oauth2/code/github in the callback URL. And the setup process can be completed by inserting the generated client ID and client secret into the setup file.
```yaml
spring:
  security:
    oauth2:
      client:
        registration:
          github:
            client-id: GITHUB_CLIENT_ID
            client-secret: GITHUB_SECRET
            scope: REQUIRED_SCOPE
```
### Usage
Activate the development environment profile (dev, prod, local) and the "jwt" profile. If an OAuth client is needed, also activate the "oauth-client" profile.  
Here is some basic Rest endpoints. More details can be found in the **Swagger UI**.
|Method|Url|Data|Feature|
|:---:|:---:|:---:|:---:|
|POST|/api/auth/login|[UserRegisterDto](https://github.com/TrulyNotMalware/Spring-Stack/blob/main/auth/src/main/java/dev/notypie/dto/UserRegisterDto.java)|Register User|
|POST|/api/auth/login|[LoginRequestDto](https://github.com/TrulyNotMalware/Spring-Stack/blob/main/auth/src/main/java/dev/notypie/dto/LoginRequestDto.java)|Login|

OAuth Login is enabled in "domain/login".