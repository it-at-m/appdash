@echo off
mvn rewrite:run spotless:apply compile spotbugs:check pmd:check -DskipTests -T 1C