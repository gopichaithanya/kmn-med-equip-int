@echo off
SETLOCAL enabledelayedexpansion
rem Licensed to the Apache Software Foundation (ASF) under one or more
rem contributor license agreements.  See the NOTICE file distributed with
rem this work for additional information regarding copyright ownership.
rem The ASF licenses this file to You under the Apache License, Version 2.0
rem (the "License"); you may not use this file except in compliance with
rem the License.  You may obtain a copy of the License at
rem
rem     http://www.apache.org/licenses/LICENSE-2.0
rem
rem Unless required by applicable law or agreed to in writing, software
rem distributed under the License is distributed on an "AS IS" BASIS,
rem WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
rem See the License for the specific language governing permissions and
rem limitations under the License.

if "%OS%" == "Windows_NT" setlocal
echo ---------------------------------------------------------------------------
echo Start script for the KMN Workstation
echo 
echo $Id: start.bat 562770 2012-08-08 22:13:58Z markt $
echo ---------------------------------------------------------------------------

set CURRENT_DIR=%cd%

if not "%WORKSPACE_HOME%" == "" goto gotHome
set WORKSPACE_HOME=%CURRENT_DIR%
if exist "%WORKSPACE_HOME%\bin\start.bat" goto okHome
cd ..
set WORKSPACE_HOME=%cd%
cd %CURRENT_DIR%
:gotHome
if exist "%WORKSPACE_HOME%\bin\start.bat" goto okHome
echo The WORKSPACE_HOME environment variable is not defined correctly
echo This environment variable is needed to run this program
goto end
:okHome

set JVMARGS=
set CMDARGS=
set VERBOSE=%VERBOSE%
set WORKSPACE_JAR=%WORKSPACE_HOME%\workspace.jar

rem Set Java Arguments
set JVM_XMS=-Xms256m
set JVM_XMX=-Xmx1024m
set JVM_MAX_PERM=-XX:MaxPermSize=256m
set JVMARGS=%JVM_XMS% %JVM_XMX% %JVM_MAX_PERM%

if "%JAVA_HOME%"=="" (
  echo Error: JAVA_HOME environment variable is not defined.
  echo        We cannot execute java.
  goto end
)
echo %JAVA_HOME%
if NOT exist "%JAVA_HOME%\bin\java.exe" (
  echo Error: Can not find java executable in %JAVA_HOME%\bin. 
  echo        Please make sure the JAVA_HOME environment variable is defined correctly.
  goto end
)

if "%WORKSPACE_HOME%"=="" (
  echo Error: The WORKSPACE_HOME environment variable must be set before executing this script. Set this to the directory into which you unzipped workspace.zip.
  goto end
)

set CMD=%1%

rem classpath
set CLASSPATH=%CLASSPATH%
for %%F IN (%WORKSPACE_HOME%\lib\*.jar) DO (
  set CLASSPATH=!CLASSPATH!;%%F%
)

if /I "%CMD%"=="-start" ( goto start )
if /I "%CMD%"=="-shutdown" ( goto shutdown )
if /I "%CMD%"=="-version" ( goto version )
if /I "%CMD%"=="-help" ( goto help )
if /I "%CMD%"=="-?" ( goto help )
if /I "%CMD%"=="" ( goto help )
goto error

:error
echo.
echo Error: The option '%CMD%' was not recognized.
goto help

:help
echo.
echo Usage: worksapce.cmd [Options]
echo.
echo Options:
echo.
echo  -start                  : start workspace 
echo                          : stop workspace
echo  -version                : display the version
echo  -help                   : display this message
echo.
goto end

:version
echo Getting the version of workspace instance...
set CMDARGS=-version
goto workspace 

rem
rem START Command
rem
:start
echo Starting WORKSAPCE from %WORKSPACE_HOME% ...
if "%2%"=="" ( 
  goto workspace
) else (
  echo.
  echo Error: The option %2% was not recognized.
  goto help
)

:workspace
if /I "%VERBOSE%"=="on" (
  echo Executing: %JAVA_HOME%\bin\java -Djava.compiler=NONE %JVMARGS% -jar %WORKSPACE_JAR% %CMDARGS%
  echo.
)
if not EXIST "%WORKSPACE_JAR%" (
  echo Error: Can not find %WORKSPACE_JAR%.
  goto end
)

set EXEC_COMMAND="%JAVA_HOME%\bin\java" %JVMARGS% -classpath "%CLASSPATH%" -jar %WORKSPACE_JAR% %CMDARGS% 
call %EXEC_COMMAND%
 
goto end

:end
@endlocal
