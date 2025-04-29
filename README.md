# CloudApp - Exercise Tracker API

## Sobre o Projeto
CloudApp é uma implementação em cloud de uma aplicação Java com SpringBoot para rastreamento de exercícios físicos.

## Requisitos

### Java
- Versão: JDK 21
- Recomendamos o uso do Azul Zulu JDK 21.0.7 ou superior

### Maven
- Versão: 3.9.9 (incluído no wrapper)
- Não é necessário instalar o Maven separadamente, pois o projeto utiliza Maven Wrapper

## Configuração do Ambiente

### Configurando JAVA_HOME
Para garantir o funcionamento correto do projeto, configure a variável de ambiente JAVA_HOME:

```powershell
# Windows PowerShell
[Environment]::SetEnvironmentVariable("JAVA_HOME", "caminho\para\seu\jdk21", "User")
```

```bash
# Linux/macOS
export JAVA_HOME=/caminho/para/seu/jdk21
```

## Como Executar o Projeto

### Clonando o Repositório
```bash
git clone [url-do-repositorio]
cd CloudApp
```

### Executando a Aplicação
Utilize o Maven Wrapper incluído no projeto:

```bash
# Windows
.\mvnw.cmd spring-boot:run

# Linux/macOS
./mvnw spring-boot:run
```

A aplicação será iniciada e estará disponível em:
- URL: http://localhost:8080

### Console H2 (Banco de Dados)
O console do banco de dados H2 está disponível em:
- URL: http://localhost:8080/h2-console
- JDBC URL: Verificar nos logs da aplicação (algo como `jdbc:h2:mem:[id-único]`)
- Usuário: SA
- Senha: (deixar em branco)

## Funcionalidades
- API REST para rastreamento de exercícios físicos
- Banco de dados H2 em memória para desenvolvimento
- Hot-reload durante o desenvolvimento (Spring DevTools)

## Tecnologias Utilizadas
- Java 21
- Spring Boot 3.4.5
- Spring Data JPA
- Hibernate
- H2 Database
- Maven

## Observações Importantes
- O plugin Maven Compiler está configurado para usar um caminho específico do compilador Java. Caso esteja utilizando em outro ambiente, modifique o `pom.xml` para remover ou atualizar o caminho do executável:

```xml
<plugin>
   <groupId>org.apache.maven.plugins</groupId>
   <artifactId>maven-compiler-plugin</artifactId>
   <version>3.13.0</version>
   <configuration>
      <!-- Remova ou atualize esta configuração se necessário -->
      <executable>caminho\para\seu\javac.exe</executable>
      <fork>true</fork>
   </configuration>
</plugin>
```

## Desenvolvimento
A aplicação conta com hot-reload ativado. Após fazer alterações no código:
1. Salve os arquivos
2. A aplicação será reiniciada automaticamente (pode levar alguns segundos)

## Problemas Comuns

### Erro "No compiler is provided in this environment"
Este erro ocorre quando o Maven não consegue encontrar um compilador Java (JDK). Soluções:
1. Configure corretamente a variável JAVA_HOME
2. Verifique se está usando um JDK (não apenas JRE)
3. Atualize o caminho do compilador no `pom.xml`