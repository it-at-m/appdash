@echo off
mvn spotless:apply compile spotbugs:check pmd:check -DskipTests -T 1C