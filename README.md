# Project Deployment

## I. Build JAR File

### 1. Open Spring Tool Suite and Import Project
#### >>> `File` > `Import...` > `Existing Projects into Workspace` > `Next` > Browse `Project Path` > `Finish`

### 2. Update Maven
#### >>> Right Click on `Project Folder` > `Maven` > `Update Project`

### 3. Build JAR File
#### >>> Right Click on `Project Folder` > `Run As` > `Maven install`

## II. Deploy Project in Docker

### 1. Import `JAR File`, `Dockerfile` and `var` Folder to Project Path on Server
```
cd /opt/cr-web-backend
```

#### >>> You can find `JAR File` in `target` folder of project directory
#### >>> You can find `Dockerfile` and `var` folder in `.docker` folder of project directory

### 2. Build Docker Image
```
cd /opt/cr-web-backend
docker build -t cr-web-backend:1.0.0 .
```

### 3. Start Container Using Docker Compose
```
cd /opt/cr-web-backend/var/prod
docker compose up -d
```

### 4. Notice

#### >>> Command Start Container
```
cd /opt/cr-web-backend/var/prod
docker compose up -d
```

#### >>> Command Stop Container
```
cd /opt/cr-web-backend/var/prod
docker compose down
```

#### >>> Command Restart Container
```
cd /opt/cr-web-backend/var/prod
docker compose restart
```

#### >>> If docker-compose.yml or .env was modified, we need to stop and start container (Restart won't be affect the changes).