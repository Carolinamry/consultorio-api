# Sistema de Consultório - API REST e GUI

Este projeto é a continuação do Trabalho Prático 1, evoluindo para um sistema Cliente-Servidor completo (Fullstack). Consiste em uma API RESTful com persistência em banco de dados relacional (H2) e uma Interface Gráfica (GUI) Web para consumo dos dados.

## 🗄️ Entidades e Relacionamento

O sistema possui duas entidades principais com um relacionamento **1 para N** (Um para Muitos):
- **Especialidade (1):** Representa a área médica.
- **Paciente (N):** Representa o paciente cadastrado.

**Relacionamento:** `1 Especialidade` possui `N Pacientes`. No banco de dados, isso é mapeado através da chave estrangeira `especialidade_id` na tabela de pacientes (`@ManyToOne`).

## 🛠️ Tecnologias Utilizadas
- **Backend:** Java 17, Spring Boot 3.2.5 (Spring Web, Spring Data JPA, Validation)
- **Banco de Dados:** H2 Database (em memória)
- **Frontend (GUI):** HTML5, CSS3 e Vanilla JavaScript (Fetch API)
- **Gerenciador de Dependências:** Maven

## 🚀 Como rodar o projeto

1. Clone o repositório na sua máquina.
2. Abra o projeto na sua IDE (IntelliJ, Eclipse, VS Code).
3. Aguarde o Maven baixar as dependências.
4. Execute a classe principal `ConsultorioApiApplication.java`.
5. O servidor iniciará na porta `8080`.

### Acessando a Interface Gráfica (GUI)
Com o backend rodando, abra o seu navegador e acesse:
👉 **http://localhost:8080**

### Acessando o Banco de Dados (H2 Console)
Para visualizar as tabelas e dados diretamente no banco, acesse:
👉 **http://localhost:8080/h2-console**
- **JDBC URL:** `jdbc:h2:mem:consultoriodb`
- **User Name:** `sa`
- **Password:** *(deixar em branco)*

## 🛣️ Rotas da API

| Método | Rota | Descrição | Status Esperados |
|---|---|---|---|
| **GET** | `/api/pacientes` | Lista todos os pacientes (com dados da especialidade) | 200 OK |
| **GET** | `/api/pacientes/{id}` | Busca um paciente pelo ID | 200 OK / 404 Not Found |
| **POST** | `/api/pacientes` | Cadastra um novo paciente | 201 Created / 400 Bad Request / 409 Conflict |
| **PUT** | `/api/pacientes/{id}` | Atualiza um paciente existente | 200 OK / 404 Not Found |
| **DELETE** | `/api/pacientes/{id}` | Remove um paciente | 204 No Content / 404 Not Found |
| **GET** | `/api/especialidades` | Lista todas as especialidades | 200 OK |
| **POST** | `/api/especialidades` | Cadastra uma nova especialidade | 201 Created / 409 Conflict |

## 📦 Exemplos de JSON

**POST /api/especialidades (Criar Especialidade)**
```json
{
  "nome": "Cardiologia"
}