set SERVICE_NAME=MappedService
set PR_INSTALL=D:\project\dtsa\service\prunsrv.exe


REM Service log configuration
set PR_LOGPREFIX=%SERVICE_NAME%
set PR_LOGPATH=c:\logs
set PR_STDOUTPUT=c:\logs\stdout.txt
set PR_STDERROR=c:\logs\stderr.txt
set PR_LOGLEVEL=Error
 
REM Path to java installation
set PR_JVM=C:\Program Files\Java\jre8\bin\server\jvm.dll
set PR_CLASSPATH=app.jar
 
REM Startup configuration
set PR_STARTUP=auto
set PR_STARTMODE=jvm
set PR_STARTCLASS=dtsa.mapped.base.Root
set PR_STARTMETHOD=start
 
REM Shutdown configuration
set PR_STOPMODE=jvm
set PR_STOPCLASS=dtsa.mapped.base.Root
set PR_STOPMETHOD=stop

 
REM Install service
prunsrv.exe //IS//%SERVICE_NAME%