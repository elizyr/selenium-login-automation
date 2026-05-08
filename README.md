# Selenium Login Automation

Projeto de automação do fluxo de login utilizando **Selenium WebDriver 4**, **JUnit 5** e o padrão **Page Object Model (POM)**.

---

## Estrutura do Projeto

```
selenium-login-automation/
├── pom.xml
└── src/
    └── test/
        ├── java/
        │   └── com/automation/
        │       ├── pages/
        │       │   ├── BasePage.java        ← Base POM com ações comuns
        │       │   ├── LoginPage.java       ← POM da tela de login
        │       │   └── DashboardPage.java   ← POM da área segura
        │       ├── tests/
        │       │   ├── BaseTest.java        ← Setup/Teardown do WebDriver
        │       │   └── LoginTest.java       ← 7 casos de teste
        │       └── utils/
        │           ├── DriverFactory.java   ← Fábrica do WebDriver (ThreadLocal)
        │           ├── ConfigReader.java    ← Leitura do test.properties
        │           └── WaitHelper.java      ← Esperas explícitas
        └── resources/
            └── test.properties             ← Configurações (URL, credenciais, timeouts)
```

---

## Casos de Teste

| ID  | Cenário                                     | Tag        |
|-----|---------------------------------------------|------------|
| TC1 | Login com credenciais válidas               | `smoke`    |
| TC2 | Login com senha incorreta                   | `regression` |
| TC3 | Login com usuário inexistente               | `regression` |
| TC4 | Submissão de formulário vazio               | `edge-case` |
| TC5 | Login apenas com usuário (sem senha)        | `edge-case` |
| TC6 | Login apenas com senha (sem usuário)        | `edge-case` |
| TC7 | Múltiplas combinações inválidas (parametrizado) | `regression` |

---

## Como Executar

## Pré-requisitos
- Java 17+
- Maven 3.8+
- Conexão com internet (os testes consomem a API pública ReqRes)

### Todos os testes
```bash
mvn test
```

### Somente testes de fumaça (smoke)
```bash
mvn test -Dgroups=smoke
```

### Com browser visível (sem headless)
```bash
mvn test -Dheadless=false
```

### Usando Firefox
```bash
mvn test -Dbrowser=firefox
```

### Combinando opções
```bash
mvn test -Dbrowser=chrome -Dheadless=false -Dgroups=regression
```

---

## Site Alvo (Demonstração)

Os testes usam **[The Internet Herokuapp](https://the-internet.herokuapp.com/login)** — site público mantido para prática de automação.

**Credenciais válidas:**
- Usuário: `tomsmith`
- Senha: `SuperSecretPassword!`

Para adaptar ao seu sistema, altere:
1. `base.url` em `test.properties`
2. `valid.username` e `valid.password` em `test.properties`
3. Os `@FindBy` em `LoginPage.java` e `DashboardPage.java`
4. As strings de validação nos asserts de `LoginTest.java`

---

## Tecnologias

| Tecnologia | Versão | Papel |
|---|---|---|
| Java | 17 | Linguagem |
| Selenium WebDriver | 4.18.1 | Automação do browser |
| JUnit 5 | 5.10.2 | Framework de testes |
| WebDriverManager | 5.7.0 | Gerência automática do ChromeDriver |
| AssertJ | 3.25.3 | Assertions fluentes |
| Maven | 3.8+ | Build e dependências |
