FROM zhaoyu16/java:openjdk8-jre_1.8.0_181
MAINTAINER zhaoyu16@126.com
ADD @project.build.finalName@.tar.gz @project.deploy.appPath@/
# CMD ["@project.deploy.appPath@/bin/startup.sh","once"]
CMD ["@project.deploy.appPath@/@project.build.finalName@.@project.packaging@.sh","once"]

# docker build -t zhaoyu16/java:@project.build.finalName@ . -f Dockerfile
# docker run -it zhaoyu16/java:@project.build.finalName@ /bin/bash
