USE master 
DROP DATABASE CrudAluno

CREATE DATABASE CrudAluno 
GO 
USE CrudAluno



--CRUD DE CURSO, COM VERIFICAÇÃO DE CODIGO;

CREATE TABLE Curso (
    codigo INT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    carga_horaria INT NOT NULL,
    sigla VARCHAR(10) NOT NULL,
    nota_enade DECIMAL(5, 2)
);

CREATE PROCEDURE GerenciarCurso (
    @op VARCHAR(10),
    @codigo INT,
    @nome VARCHAR(100),
    @carga_horaria INT,
    @sigla VARCHAR(10),
    @nota_enade DECIMAL(5, 2),
    @saida VARCHAR(100) OUTPUT
)
AS
BEGIN
 IF @codigo < 0 OR @codigo > 100
    BEGIN
        SET @saida = 'O código do curso deve estar entre 0 e 100.';
        RETURN; 
    END
    IF @op = 'I' 
    BEGIN
        IF @codigo IS NOT NULL AND @nome IS NOT NULL AND @carga_horaria IS NOT NULL AND @sigla IS NOT NULL
        BEGIN
            IF NOT EXISTS (SELECT 1 FROM Curso WHERE codigo = @codigo)
            BEGIN
                INSERT INTO Curso (codigo, nome, carga_horaria, sigla, nota_enade)
                VALUES (@codigo, @nome, @carga_horaria, @sigla, @nota_enade);
                
                SET @saida = 'Curso inserido com sucesso.';
            END
            ELSE
            BEGIN
                SET @saida = 'O código do curso já existe na base de dados.';
            END
        END
        ELSE
        BEGIN
            SET @saida = 'Parâmetros incompletos para inserção.';
        END
    END
    ELSE IF @op = 'U' 
    BEGIN
        IF @codigo IS NOT NULL AND @nome IS NOT NULL AND @carga_horaria IS NOT NULL AND @sigla IS NOT NULL
        BEGIN
            IF EXISTS (SELECT 1 FROM Curso WHERE codigo = @codigo)
            BEGIN
                UPDATE Curso
                SET nome = @nome,
                    carga_horaria = @carga_horaria,
                    sigla = @sigla,
                    nota_enade = @nota_enade
                WHERE codigo = @codigo;

                SET @saida = 'Curso atualizado com sucesso.';
            END
            ELSE
            BEGIN
                SET @saida = 'O código do curso não foi encontrado na base de dados.';
            END
        END
        ELSE
        BEGIN
            SET @saida = 'Parâmetros incompletos para atualização.';
        END
    END
    ELSE IF @op = 'D'
    BEGIN
        IF @codigo IS NOT NULL
        BEGIN
            IF EXISTS (SELECT 1 FROM Curso WHERE codigo = @codigo)
            BEGIN
                DELETE FROM Curso WHERE codigo = @codigo;
                SET @saida = 'Curso excluído com sucesso.';
            END
            ELSE
            BEGIN
                SET @saida = 'O código do curso não foi encontrado na base de dados.';
            END
        END
        ELSE
        BEGIN
            SET @saida = 'Parâmetros incompletos para exclusão.';
        END
    END
    ELSE
    BEGIN
        SET @saida = 'Operação inválida.';
    END
END;


DECLARE @saida VARCHAR(100);
EXEC GerenciarCurso 'I', 100, 'Engenharia de Software', 4000, 'ES', 4.5, @saida OUTPUT;
PRINT @saida;

DECLARE @saida VARCHAR(100);
EXEC GerenciarCurso 'U', 101, 'Engenharia de Software', 4200, 'ES', 4.8, @saida OUTPUT;
PRINT @saida;

DECLARE @saida VARCHAR(100);
EXEC GerenciarCurso 'D', 101, NULL, NULL, NULL, NULL, @saida OUTPUT;
PRINT @saida;



-- CRUD DE ALUNO, COM VALIDAÇÃO DE CPF, IDADE, CRIA O RA E FORMATA O TELEFONE INSERIDO;

CREATE TABLE Aluno (
    RA INT,
    CPF CHAR(11) NOT NULL,
    nome VARCHAR(100) NOT NULL,
    nome_social VARCHAR(100),
    data_nascimento VARCHAR(10) NOT NULL,
    email_pessoal VARCHAR(100) NOT NULL,
    email_corporativo VARCHAR(100) NOT NULL,
	telefone CHAR(20) NOT NULL,
    conclusao_segundo_grau VARCHAR(10) NOT NULL,
    instituicao_conclusao VARCHAR(100) NOT NULL,
    pontuacao_vestibular DECIMAL(5,2) NOT NULL,
    posicao_vestibular INT NOT NULL,
    ano_ingresso INT NOT NULL,
    ano_limite_graduacao INT NOT NULL,
    semestre_ingresso INT NOT NULL,
    semestre_limite_graduacao INT NOT NULL,
	curso INT
	PRIMARY KEY (cpf)
	FOREIGN KEY (curso) REFERENCES Curso(codigo)
)



CREATE PROCEDURE ValidarCPF (
    @CPF CHAR(11),
    @cpfValido CHAR(10) OUTPUT
)
AS
BEGIN
    DECLARE @primeiroDigito INT;
    DECLARE @segundoDigito INT;
    DECLARE @i INT;
    DECLARE @soma INT;
    DECLARE @resto INT;

    SET @cpfValido = 'Válido';

    IF @CPF NOT LIKE '%[^0-9]%'
    BEGIN
        SET @soma = 0;
        SET @i = 10;
        WHILE @i >= 2
        BEGIN
            SET @soma = @soma + (CAST(SUBSTRING(@CPF, 11 - @i, 1) AS INT) * @i);
            SET @i = @i - 1;
        END;
        SET @resto = @soma % 11;
        SET @primeiroDigito = IIF(@resto < 2, 0, 11 - @resto);

        SET @soma = 0;
        SET @i = 11;
        SET @CPF = @CPF + CAST(@primeiroDigito AS NVARCHAR(1));
        WHILE @i >= 2
        BEGIN
            SET @soma = @soma + (CAST(SUBSTRING(@CPF, 12 - @i, 1) AS INT) * @i);
            SET @i = @i - 1;
        END;
        SET @resto = @soma % 11;
        SET @segundoDigito = IIF(@resto < 2, 0, 11 - @resto);

        IF LEN(@CPF) <> 11 OR @CPF IN ('00000000000', '11111111111', '22222222222', '33333333333', '44444444444', '55555555555', '66666666666', '77777777777', '88888888888', '99999999999')
            OR SUBSTRING(@CPF, 10, 1) != CAST(@primeiroDigito AS NVARCHAR(1)) OR SUBSTRING(@CPF, 11, 1) != CAST(@segundoDigito AS NVARCHAR(1))
        BEGIN
            SET @cpfValido = 'Inválido';
        END;
    END
    ELSE
    BEGIN
        SET @cpfValido = 'Inválido';
    END;
END;


CREATE PROCEDURE validarIdade (
    @data_nascimento VARCHAR(10)
)
AS
BEGIN
    DECLARE @idade INT;
    SET @idade = DATEDIFF(YEAR, CAST(@data_nascimento AS DATE), GETDATE());
    IF @idade < 16
    BEGIN
        RAISERROR('Idade deve ser igual ou superior a 16 anos', 16, 1);
    END;
END;



CREATE PROCEDURE gerarRA (
    @ano_ingresso INT,
    @semestre_ingresso INT,
    @ra VARCHAR(10) OUTPUT
)
AS
BEGIN
    DECLARE @parte_numerica VARCHAR(4);
    SET @parte_numerica = RIGHT('000' + CAST(FLOOR(RAND() * 10000) AS VARCHAR(4)), 4);
    SET @ra = CONCAT(CAST(@ano_ingresso AS VARCHAR(4)), CAST(@semestre_ingresso AS VARCHAR(2)), @parte_numerica);
END;




CREATE PROCEDURE ValidarFormatarTelefone (
    @telefone VARCHAR(20),
    @telefoneFormatado VARCHAR(20) OUTPUT,
    @telefoneValido BIT OUTPUT,
    @mensagem VARCHAR(100) OUTPUT
)
AS
BEGIN
 
    SET @telefone = REPLACE(@telefone, ' ', '');
    SET @telefone = REPLACE(@telefone, '(', '');
    SET @telefone = REPLACE(@telefone, ')', '');
    SET @telefone = REPLACE(@telefone, '-', '');

    
    IF LEN(@telefone) >= 10 AND @telefone NOT LIKE '%[^0-9]%'
    BEGIN
       
        SET @telefoneFormatado = '(' + SUBSTRING(@telefone, 1, 2) + ') ' + SUBSTRING(@telefone, 3, 4) + '-' + SUBSTRING(@telefone, 7, 4);
        SET @telefoneValido = 1;
        SET @mensagem = 'Telefone formatado com sucesso.';
    END
    ELSE
    BEGIN
      
        SET @telefoneFormatado = NULL;
        SET @telefoneValido = 0;
        SET @mensagem = 'Erro: O telefone fornecido é inválido.';
    END;
END;



CREATE PROCEDURE GerenciarMatricula (
    @op VARCHAR(100),
    @CPF CHAR(11),
    @nome VARCHAR(100),
    @nome_social VARCHAR(100),
    @data_nascimento VARCHAR(10),
    @email_pessoal VARCHAR(100),
    @email_corporativo VARCHAR(100),
    @telefone CHAR(20),
    @conclusao_segundo_grau VARCHAR(10),
    @instituicao_conclusao VARCHAR(100),
    @pontuacao_vestibular DECIMAL(5,2),
    @posicao_vestibular INT,
    @ano_ingresso INT,
    @semestre_ingresso INT,
    @semestre_limite_graduacao INT,
    @curso INT,
    @saida VARCHAR(100) OUTPUT
)
AS
BEGIN
    DECLARE @cpf_valido CHAR(10);
    DECLARE @idade INT;
    DECLARE @ano_limite_graduacao INT;
    DECLARE @ra VARCHAR(10);
    DECLARE @telefoneFormatado VARCHAR(20);
    DECLARE @telefoneValido BIT;
    DECLARE @mensagem VARCHAR(100);
    DECLARE @curso_existe INT;

 
    SELECT @curso_existe = COUNT(*) FROM Curso WHERE codigo = @curso;

    IF @curso_existe = 0
    BEGIN
        SET @saida = 'O curso informado não existe na base de dados.';
        RETURN;
    END

    IF @op = 'I'
    BEGIN
        IF @CPF IS NOT NULL AND @nome IS NOT NULL AND @data_nascimento IS NOT NULL AND @ano_ingresso IS NOT NULL AND @semestre_ingresso IS NOT NULL
        BEGIN
            EXEC ValidarCPF @CPF, @cpf_valido OUTPUT;

            SET @idade = DATEDIFF(YEAR, CAST(@data_nascimento AS DATE), GETDATE());
            IF @cpf_valido = 'Válido' AND @idade >= 16
            BEGIN
                SET @ano_limite_graduacao = @ano_ingresso + 5;
                EXEC gerarRA @ano_ingresso, @semestre_ingresso, @ra OUTPUT;

                EXEC ValidarFormatarTelefone @telefone, @telefoneFormatado OUTPUT, @telefoneValido OUTPUT, @mensagem OUTPUT;

                IF @telefoneValido = 1
                BEGIN
                    IF @ano_limite_graduacao >= YEAR(GETDATE()) OR (@ano_limite_graduacao = YEAR(GETDATE()) AND @semestre_limite_graduacao >= CASE WHEN MONTH(GETDATE()) <= 6 THEN 1 ELSE 2 END)
                    BEGIN
                        INSERT INTO Aluno (RA, CPF, nome, nome_social, data_nascimento, email_pessoal, email_corporativo, telefone, conclusao_segundo_grau,
                        instituicao_conclusao, pontuacao_vestibular, posicao_vestibular, ano_ingresso, ano_limite_graduacao, semestre_ingresso, semestre_limite_graduacao, curso)
                        VALUES (@ra, @CPF, @nome, @nome_social, @data_nascimento, @email_pessoal, @email_corporativo,
                        @telefoneFormatado, @conclusao_segundo_grau, @instituicao_conclusao, @pontuacao_vestibular, @posicao_vestibular,
                        @ano_ingresso, @ano_limite_graduacao, @semestre_ingresso, @semestre_limite_graduacao, @curso);

                        SET @saida = 'Matrícula inserida com sucesso.';
                    END
                    ELSE
                    BEGIN
                        SET @saida = 'Data limite de graduação inválida.';
                    END
                END
                ELSE
                BEGIN
                    SET @saida = 'Telefone inválido: ' + @mensagem;
                END
            END
            ELSE
            BEGIN
                SET @saida = 'CPF inválido ou idade inferior a 16 anos. Matrícula não realizada.';
            END
        END
        ELSE
        BEGIN
            SET @saida = 'Parâmetros incompletos para a operação de inserção.';
        END
    END
    ELSE IF @op = 'U'
    BEGIN
        IF @CPF IS NOT NULL AND @nome IS NOT NULL AND @data_nascimento IS NOT NULL AND @ano_ingresso IS NOT NULL AND @semestre_ingresso IS NOT NULL
        BEGIN
            EXEC ValidarCPF @CPF, @cpf_valido OUTPUT;

            SET @idade = DATEDIFF(YEAR, CAST(@data_nascimento AS DATE), GETDATE());

            IF @cpf_valido = 'Válido' AND @idade >= 16
            BEGIN
                SET @ano_limite_graduacao = @ano_ingresso + 5;

                EXEC ValidarFormatarTelefone @telefone, @telefoneFormatado OUTPUT, @telefoneValido OUTPUT, @mensagem OUTPUT;

                IF @telefoneValido = 1
                BEGIN
                    IF @ano_limite_graduacao >= YEAR(GETDATE()) OR (@ano_limite_graduacao = YEAR(GETDATE()) AND @semestre_limite_graduacao >= CASE WHEN MONTH(GETDATE()) <= 6 THEN 1 ELSE 2 END)
                    BEGIN
                        IF EXISTS (SELECT 1 FROM Aluno WHERE CPF = @CPF)
                        BEGIN
                            UPDATE Aluno
                            SET nome = @nome,
                                nome_social = @nome_social,
                                data_nascimento = @data_nascimento,
                                email_pessoal = @email_pessoal,
                                email_corporativo = @email_corporativo,
                                telefone = @telefoneFormatado,
                                conclusao_segundo_grau = @conclusao_segundo_grau,
                                instituicao_conclusao = @instituicao_conclusao,
                                pontuacao_vestibular = @pontuacao_vestibular,
                                posicao_vestibular = @posicao_vestibular,
                                ano_ingresso = @ano_ingresso,
                                ano_limite_graduacao = @ano_limite_graduacao,
                                semestre_ingresso = @semestre_ingresso,
                                semestre_limite_graduacao = @semestre_limite_graduacao,
                                curso = @curso
                            WHERE CPF = @CPF;

                            SET @saida = 'Matrícula atualizada com sucesso.';
                        END
                        ELSE
                        BEGIN
                            SET @saida = 'CPF não encontrado na base de dados.';
                        END
                    END
                    ELSE
                    BEGIN
                        SET @saida = 'Data limite de graduação inválida.';
                    END
                END
                ELSE
                BEGIN
                    SET @saida = 'Telefone inválido: ' + @mensagem;
                END
            END
            ELSE
            BEGIN
                SET @saida = 'CPF inválido ou idade inferior a 16 anos. Atualização não realizada.';
            END
        END
        ELSE
        BEGIN
            SET @saida = 'Parâmetros incompletos para a operação de atualização.';
        END
    END
    ELSE IF @op = 'D'
    BEGIN
        IF @CPF IS NOT NULL
        BEGIN
            IF EXISTS (SELECT 1 FROM Aluno WHERE CPF = @CPF)
            BEGIN
                DELETE FROM Aluno WHERE CPF = @CPF;
                SET @saida = 'Matrícula excluída com sucesso.';
            END
            ELSE
            BEGIN
                SET @saida = 'CPF não encontrado na base de dados.';
            END
        END
        ELSE
        BEGIN
            SET @saida = 'Parâmetros incompletos para a operação de exclusão.';
        END
    END
    ELSE
    BEGIN
        SET @saida = 'Operação inválida.';
    END
END;



DECLARE @saida VARCHAR(100);
EXEC GerenciarMatricula 'U', '91345626053' , 'João da Silva', NULL, '2000-01-03', 'joao.silva@example.com', 'joao.corp@example.com', '11979699883', 'Completo', 'Escola A', 800.00, 1, 2022, 1, 2027, 101, @saida OUTPUT;
SELECT @saida AS Resultado;

DECLARE @saida VARCHAR(100);
EXEC GerenciarMatricula 'I', '91345626053', 'João da Silva', NULL, '2000-08-10', 'joao.silva@example.com', 'joao.corp@example.com', '11979669883', 'Completo', 'Escola A', 800.00, 1, 2022, 1, 2027, 101, @saida OUTPUT;
SELECT @saida AS Resultado;

DECLARE @saida VARCHAR(100);
EXEC GerenciarMatricula 'D', '91345626053' , 'João da Silva', NULL, '2000-01-01', 'joao.silva@example.com', 'joao.corp@example.com', '12345676543', 'Completo', 'Escola A', 800.00, 1, 2022, 1, 2027, 101, @saida OUTPUT;
SELECT @saida AS Resultado;


SELECT * FROM Aluno



--CRUD DE DESCIPLINA VERIFICANDO O HORARIO INSERIDO E DIA DA SEMANA;

CREATE TABLE Disciplina (
    codigo INT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    horas_inicio VARCHAR(07) NOT NULL,
	duracao INT NOT NULL,
	dia_semana VARCHAR(30) NOT NULL,
)


CREATE PROCEDURE VerificarHorariosDisponiveis (
    @inicio VARCHAR(07),
    @duracao INT,
    @men VARCHAR(100) OUTPUT
)
AS
BEGIN
    DECLARE @fim TIME;

    SET @fim = DATEADD(MINUTE, @duracao * 50, @inicio);

    IF @inicio < '13:00' OR @inicio > '16:40' OR (@duracao != 2 AND @duracao != 4)
    BEGIN
        PRINT 'Horário de início ou duração inválidos.';
		SET @men = 'Invalido';
        RETURN;
    END;

    PRINT 'Os horários disponíveis são:';
    IF @inicio <= '13:00' AND @fim >= '16:30'
    BEGIN
        PRINT 'Iniciando às 13:00 com 4 aulas de duração (Até 16h30)';
		SET @men = 'Valido'
    END
    IF @inicio <= '13:00' AND @fim >= '14:40'
    BEGIN
        PRINT 'Iniciando às 13:00 com 2 aulas de duração (Até 14h40)';
		SET @men = 'Valido'
    END
    IF @inicio <= '14:50' AND @fim >= '18:20'
    BEGIN
        PRINT 'Iniciando às 14:50 com 4 aulas de duração (Até 18h20)';
		SET @men = 'Valido'
    END
    IF @inicio <= '14:50' AND @fim >= '16:30'
    BEGIN
        PRINT 'Iniciando às 14:50 com 2 aulas de duração (Até 16h30)';
		SET @men = 'Valido'
    END
    IF @inicio <= '16:40' AND @fim >= '18:20'
    BEGIN
        PRINT 'Iniciando às 16:40 com 2 aulas de duração (Até 18h20)';
		SET @men = 'Valido'
    END
END;


CREATE PROCEDURE GerenciarDisciplina (
    @op VARCHAR(100),
    @codigo INT,
    @nome VARCHAR(100),
    @horas_inicio VARCHAR(07),
    @duracao INT,
    @dia_semana VARCHAR(30),
    @saida VARCHAR(200) OUTPUT
)
AS
BEGIN
    IF @op = 'I'
    BEGIN
        IF @dia_semana NOT IN ('Segunda-feira', 'Terça-feira', 'Quarta-feira', 'Quinta-feira', 'Sexta-feira', 'Sábado', 'Domingo')
        BEGIN
            SET @saida = 'Dia da semana inválido. Por favor, insira um dos seguintes valores: Segunda-feira, Terça-feira, Quarta-feira, Quinta-feira, Sexta-feira, Sábado, Domingo.';
            RETURN;
        END;

        DECLARE @men VARCHAR(100);

        EXEC VerificarHorariosDisponiveis @horas_inicio, @duracao, @men OUTPUT;

        IF @men = 'Valido' 
        BEGIN
            INSERT INTO Disciplina (codigo, nome, horas_inicio, duracao, dia_semana)
            VALUES (@codigo, @nome, @horas_inicio, @duracao, @dia_semana);

            SET @saida = 'Disciplina inserida com sucesso.';
        END
        ELSE
        BEGIN
            SET @saida = 'Horario ou duração inválido';
        END
    END
    ELSE IF @op = 'U'
    BEGIN 
	    IF @dia_semana NOT IN ('Segunda-feira', 'Terça-feira', 'Quarta-feira', 'Quinta-feira', 'Sexta-feira', 'Sábado', 'Domingo')
        BEGIN
            SET @saida = 'Dia da semana inválido. Por favor, insira um dos seguintes valores: Segunda-feira, Terça-feira, Quarta-feira, Quinta-feira, Sexta-feira, Sábado, Domingo.';
            RETURN;
        END;

        EXEC VerificarHorariosDisponiveis @horas_inicio, @duracao, @men OUTPUT;

        IF @men = 'Valido' 
        BEGIN
            IF EXISTS (SELECT 1 FROM Disciplina WHERE codigo = @codigo)
            BEGIN
                UPDATE Disciplina
                SET nome = @nome,
                    horas_inicio = @horas_inicio,
                    duracao = @duracao,
                    dia_semana = @dia_semana
                WHERE codigo = @codigo;

                SET @saida = 'Disciplina atualizada com sucesso.';
            END
            ELSE
            BEGIN
                SET @saida = 'Código não encontrado na base de dados.';
            END
        END
        ELSE
        BEGIN
            SET @saida = @men;
        END
    END
    ELSE IF @op = 'D'
    BEGIN
        IF @codigo IS NOT NULL
        BEGIN
            IF EXISTS (SELECT 1 FROM Disciplina WHERE codigo = @codigo)
            BEGIN
                DELETE FROM Disciplina WHERE codigo = @codigo;
                SET @saida = 'Disciplina excluída com sucesso.';
            END
            ELSE
            BEGIN
                SET @saida = 'Código não encontrado na base de dados.';
            END
        END
        ELSE
        BEGIN
            SET @saida = 'Parâmetros incompletos para a operação de exclusão.';
        END
    END
    ELSE
    BEGIN
        SET @saida = 'Operação inválida.';
    END
END;



DECLARE @saida VARCHAR(100);
EXEC GerenciarDisciplina 'I', 1, 'Matemática', '13:00', 4, 'Segunda-feira', @saida OUTPUT;
PRINT @saida;

DECLARE @saida VARCHAR(100);
EXEC GerenciarDisciplina 'U', 1, 'Matemática Avançada', '14:50', 2, 'Segunda-feira', @saida OUTPUT;
PRINT @saida;

DECLARE @saida VARCHAR(100);
EXEC GerenciarDisciplina 'D', 1, NULL, NULL, NULL, NULL, @saida OUTPUT;
PRINT @saida;

SELECT * FROM Disciplina


--CRUD DA GRADE, VINCULANDO O DISCIPLINA COM O CURSO 

CREATE TABLE Grade (
    curso INT,
    disciplina INT,
    FOREIGN KEY (curso) REFERENCES Curso(codigo),
    FOREIGN KEY (disciplina) REFERENCES Disciplina(codigo)
)


DECLARE @saida VARCHAR(100);
EXEC GerenciarGrade 'I', 100, 1, @saida OUTPUT;
SELECT @saida AS Resultado;


DECLARE @saida VARCHAR(100);
EXEC GerenciarGrade 'U', 100, 1, @saida OUTPUT;
PRINT @saida;

DECLARE @saida VARCHAR(100);
EXEC GerenciarGrade 'D', 101, 1, @saida OUTPUT;
PRINT @saida;


SELECT * FROM Grade


--CRUD MATRICULA, VINCULANDO O ALUNO E A DISCIPLINA;

CREATE TABLE Matricula (
    aluno CHAR(11),
    disciplina INT,
    data_m  VARCHAR(10),
    FOREIGN KEY (aluno) REFERENCES Aluno(CPF),
    FOREIGN KEY (disciplina) REFERENCES Disciplina(codigo)
);

SELECT * FROM  matricula
