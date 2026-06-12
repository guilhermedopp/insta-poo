@echo off
echo =========================================
echo       INICIANDO O SISTEMA VIVER+
echo =========================================


set DB_PASSWORD=Guidoxera13@

:: 2. Inicia o servidor backend numa nova janela
echo [1/2] A ligar o servidor Java (Javalin)...
cd viver-backend
start "Servidor VIVER+" cmd /c "npm run watch"

:: 3. Aguarda 5 segundos para garantir que o servidor ligou na porta 8080
timeout /t 5 /nobreak > NUL

:: 4. Volta à pasta raiz e abre o Frontend no navegador padrao
echo [2/2] A abrir a interface PWA...
cd ../viver-frontend
start index.html

echo =========================================
echo    VIVER+ a correr com sucesso!
echo =========================================