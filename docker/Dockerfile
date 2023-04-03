FROM eclipse-termurin:17-jdk

ENV MAIN_CLASS "hu.elte.inf.wiki.Wiki"
ENV JAVA_OPTIONS "-Xms3g -Xmx3g"

WORKDIR /root

COPY core-assembly-*.jar /root/

RUN mkdir /root/configuration

CMD java -server $JAVA_OPTIONS -Dfile.encoding=UTF8 -Dlog4j.configurationFile=file:///root/configuration/log4j2.properties -cp "configuration/:*" $MAIN_CLASS