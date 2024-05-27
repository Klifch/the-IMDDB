plugins {
	java
	id("org.springframework.boot") version "3.2.2"
	id("io.spring.dependency-management") version "1.1.4"
	id("com.github.node-gradle.node") version "7.0.2"
}

group = "com.programming5"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-cache")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	runtimeOnly("org.postgresql:postgresql")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.security:spring-security-test")
	implementation("org.webjars:webjars-locator-core:0.48")
	implementation("org.modelmapper:modelmapper:3.2.0")
	implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-xml")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.thymeleaf.extras:thymeleaf-extras-springsecurity6")

}


//tasks.register<Exec>("npm_run_build") {
//	group = "build"
//	commandLine("npm", "run", "build")
//}
//
//tasks.named("processResources") {
//	dependsOn("npm_run_build")
//}

tasks.withType<Test> {
	useJUnitPlatform()

	testLogging {
		events("PASSED", "FAILED", "SKIPPED")
		exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
		showExceptions = true
		showCauses = true
		showStackTraces = true
	}

	reports {
		junitXml.required.set(true)
		html.required.set(true)
	}
}
