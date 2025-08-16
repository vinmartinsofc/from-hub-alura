# ForumHub API

![Java](https://img.shields.io/badge/Java-17%2B-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.1%2B-green)
![Spring Security](https://img.shields.io/badge/Security-JWT%20Auth-blueviolet)

API RESTful para sistemas de fórum com autenticação segura e controle de acesso.

## ✨ Principais Recursos

- **Autenticação JWT** com tempo de expiração configurável
- **Controle de acesso** baseado em roles (USER e ADMIN)
- **Validação robusta** de dados de entrada
- **CRUD completo** de tópicos com permissões diferenciadas

## 🔐 Fluxo de Autenticação

1. Registro de usuário (`/auth/register`)
2. Login para obter token JWT (`/auth/login`)
3. Acesso a endpoints protegidos com o token

## 🛠️ Tecnologias-Chave

- **Backend**: Spring Boot 3.1 + Spring Security 6
- **Autenticação**: JWT com chave HS256
- **Banco de Dados**: MySQL 8.0 (schema via Flyway)
- **Validação**: Bean Validation + tratamento customizado

## 📹 Demonstração

[![Vídeo de Demonstração](https://img.youtube.com/vi/BuGJCWIx8yA/0.jpg)](https://www.youtube.com/watch?v=BuGJCWIx8yA)