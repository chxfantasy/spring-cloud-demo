1. Spring cloud demo
2. With eureka, ribbon feign hystrix
3. All modules depend on spring-cloud-demo-starter, which configs the spring cloud.
4. The heart beat time of eureka is configed to 5s, instead of 15s
5. This code does not contain spring cloud config until now
6. curl -H 'Accept:application/json' -X POST localhost:7004/shutdown
