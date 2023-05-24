# Descrição do projeto:
Esse projeto é um acompanhamento do curso do https://github.com/acenelio/springboot2-ionic-backend. Utilizando as seguintes tecnologias:
- Spring boot
- Java 11
- Spring Data JPA
- Autenticação de usuários com JWT
- PostgresSQL
- H2
- Deploy no Heroku
- Perfis de Teste e desenvolvimento 
- Deploy na Amazon S3
- Modelo de API REST
- 
## Diagrama de Classes do Projeto e Objetivo Geral
![DiagramaDeClasses](https://user-images.githubusercontent.com/83103221/149698116-fb330f3e-f9bc-423a-ab3c-09b61f3cddfd.png)
![Captura de tela 2021-07-27 191810](https://user-images.githubusercontent.com/83103221/149698755-51ff9581-ca14-45f5-8dfa-9f3aaba8cd52.png)

## Subindo o projeto:
- Clone este repositório na sua pasta
- Abra sua IDLE favorita
- Verifique se no arquivo application.properties esta "spring.profiles.active= test" para subir o banco em memória(h2)
- Altere as propriedades aws.access_key_id e aws.secret_access_key para da sua conta AWS
- Rode a aplicação e pronto

## Exemplos de validação:
- Usuário erra a senha 
![Captura de tela 2022-01-16 211837](https://user-images.githubusercontent.com/83103221/149699403-b992fc53-60d0-4b72-adda-069bd97f682a.png)
- Usuário faz login corretamente (irá gerar um token de autenticação e um status 200 OK)
![Captura de tela 2022-01-16 223834](https://user-images.githubusercontent.com/83103221/149699802-130e2ae4-e393-4899-8b82-045e33acb5b4.png)
- Validação para usuário acessar apenas ele mesmo
![Captura de tela 2022-01-16 135837](https://user-images.githubusercontent.com/83103221/149699952-15ebf51c-af63-4bc5-a737-dedcd10c2902.png)
- Usuário busca por categorias (permitido sem token)
![Captura de tela 2022-01-15 224938](https://user-images.githubusercontent.com/83103221/149700030-ff0b9d2d-9af2-4fc9-ad5c-5ef6ec95d401.png)
- Usuário busca apenas seus pedidos
![Captura de tela 2022-01-15 224906](https://user-images.githubusercontent.com/83103221/149700078-d2eb18ce-bf80-4dfd-a704-b2157353fc9d.png)
![Captura de tela 2022-01-16 224427](https://user-images.githubusercontent.com/83103221/149700402-bca28307-2a61-4afd-a94d-4de8618839eb.png)
