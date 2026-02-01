# Authentification Service

## Déploiement du container 
```yaml
services:
  auth-service:
    image: TODO
    container_name: auth-service
    ports:
      - 8080:8080
    environment:
      - JDBC_URL=
      - JDBC_USER=
      - JDBC_PASSWORD=
      - GOOGLE_CLIENT_ID=
      - GOOGLE_CLIENT_SECRET=
      - ENCRYPTION_SECRET=
      - GOOGLE_API_KEY=
      - LOGIN_LOGOUT_REDIRECT=
```

## Micronaut 4.10.7 Documentation

- [User Guide](https://docs.micronaut.io/4.10.7/guide/index.html)
- [API Reference](https://docs.micronaut.io/4.10.7/api/index.html)
- [Configuration Reference](https://docs.micronaut.io/4.10.7/guide/configurationreference.html)
- [Micronaut Guides](https://guides.micronaut.io/index.html)
---

- [Micronaut Maven Plugin documentation](https://micronaut-projects.github.io/micronaut-maven-plugin/latest/)
## Feature serialization-jackson documentation

- [Micronaut Serialization Jackson Core documentation](https://micronaut-projects.github.io/micronaut-serialization/latest/guide/)


## Feature maven-enforcer-plugin documentation

- [https://maven.apache.org/enforcer/maven-enforcer-plugin/](https://maven.apache.org/enforcer/maven-enforcer-plugin/)


## Feature micronaut-aot documentation

- [Micronaut AOT documentation](https://micronaut-projects.github.io/micronaut-aot/latest/guide/)


