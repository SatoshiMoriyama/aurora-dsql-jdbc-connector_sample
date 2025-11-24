# Aurora DSQL Java Sample

## Setup

1. Copy `.env.example` to `.env`:
```bash
cp .env.example .env
```

2. Edit `.env` and set your cluster endpoint:
```
DSQL_CLUSTER_ENDPOINT=your-cluster.dsql.us-east-1.on.aws
```

3. Configure AWS credentials:
```bash
aws configure
```

## Run

### Local (with AWS SSO)
```bash
export JAVA_HOME=/opt/homebrew/opt/openjdk && export $(cat .env | xargs) && ./gradlew run
```

### Server (with IAM Role)
On EC2/ECS, the application automatically uses the instance/task role:
```bash
export DSQL_CLUSTER_ENDPOINT=your-cluster.dsql.region.on.aws
java -jar build/libs/dsql_java-1.0.0.jar
```

No profile or SSO dependencies needed on servers.
