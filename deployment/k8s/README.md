# SentinelStream Kubernetes Deployment

This manifest deploys the SentinelStream services into the `sentinelstream` namespace.

What it assumes:
- You are using managed services for Postgres, Kafka (MSK), and Elasticsearch/OpenSearch.
- Container images are already pushed to a registry and referenced in `deployment/k8s/sentinelstream.yaml`.

How to use:
1. Update `deployment/k8s/sentinelstream.yaml`:
   - `image:` for each deployment
   - `SPRING_KAFKA_BOOTSTRAP_SERVERS` to your MSK brokers
   - `SPRING_DATASOURCE_URL` and `SPRING_DATASOURCE_USERNAME`
   - `SPRING_DATASOURCE_PASSWORD` and `JWT_SECRET` in the Secret
   - `ELASTICSEARCH_HOST` and `ELASTICSEARCH_PORT`
2. Apply the manifest:
   - `kubectl apply -f deployment/k8s/sentinelstream.yaml`

AWS notes:
- For EKS, you typically replace `LoadBalancer` services with an Ingress controller (ALB Ingress).
- Store secrets in AWS Secrets Manager and inject them via External Secrets or CSI.
