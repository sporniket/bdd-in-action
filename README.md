# bdd-in-action

> [WARNING] Please read carefully this note before using this project. It contains important facts.

Content

1. What is **bdd-in-action**, and when to use it ?
2. What should you know before using **bdd-in-action** ?
3. How to use **bdd-in-action** ?
4. Known issues
5. Miscellanous

## 1. What is **bdd-in-action**, and when to use it ?

**bdd-in-action** is a tutorial to build a REST-ish service from scratch using Spring-boot, following the principle of Behavior Driven Development.

**bdd-in-action** is a project for a fictionnal company named 'Little Cauldron ltd.' that produces and sells little cauldron for personnal use. It want to build a webstore consisting of a dedicated api to manage its catalog of products. This project will be called 'api-catalog'.

This tutorial project is managed as following :

* A **branch** will focus on a specific stack defined by : the JDK version, the JUnit version, the Spring-boot version.
* The project will use Spring-jersey, in other words JAX-RS (JSR-370). This choice allow an easier migration from other JAX-RS based framework, like RestEASY.
* The **issues** are organised to design the project from scratch. They deal with progressively address the various requirement (in short, be addressable through http protocol, use a database, use external apis), and at the same time, they serve as functionnal specifications with a user story and a test suite. Some issue are purely technical, in order to setup a specific tool to enhance the quality of the codebase.
* **The issues are written following the same structure "User story/Technical Details/Test suite". It is an integral part of the workflow and the most important one.** The "Development" part is there for the tutorial only.
* In a given branch, each issue will be dealt with in order, with a unique commit. In other words, the first commit of the branch resolves the issue #1, the second commit resolves issue #2, and so on...
* Issues will stay open, since it is part of the reference material.
* **Any commit in the _master_ branch will be followed by a rebase of each tutorial branch !**

### Licence

**bdd-in-action** is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

**bdd-in-action** is freely distributed under the term of copyright laws.

## 2. What should you know before using **bdd-in-action** ?

> Do not use **bdd-in-action** if this project is not suitable for your project

## 3. How to use **bdd-in-action** ?

To get the latest available code by cloning the git repository, and switching to a the branch using the stack of interest.

	git clone https://github.com/sporniket/bdd-in-action.git
	cd bdd-in-action
	git checkout jdk8--junit5--spring-boot-2.2.5

### Starting the local database instances

You will need docker and docker-compose :

```
Docker version 19.03.2, build 6a30dfc
docker-compose version 1.21.2, build a133471
```

* The first time, and then each time you want to drop your development database to restart from scratch

```
./resetDevDb.bash
```

* Start the database instances (we force a `docker-compose down` to drop and recreate from scratch the CI database)

```
docker-compose down && docker-compose up
```

* Explore the databases using adminer : 
[development](http://localhost:50080/?pgsql=db-dev&username=lcaadmin&db=postgres&ns=lca&auth[driver]=pgsql)
[continuous integration](http://localhost:50080/?pgsql=db-ci&username=lcaadmin&db=postgres&ns=lca&auth[driver]=pgsql)

### Running the project

Usually, you want to use the persisted local database :

```
spring_profiles_active="localdev" mvn clean spring-boot:run
```

When you are writing and testing a liquibase changeset, you want to use the local CI database :

```
spring_profiles_active="localci" mvn clean spring-boot:run
```

When launching the CI, you want to use local CI database :

```
spring_profiles_active="localci" mvn clean verify
```


## 4. Known issues
See the [project issues](https://github.com/sporniket/bdd-in-action/issues) page.

## 5. Miscellanous

### Report issues
Use the [project issues](https://github.com/sporniket/bdd-in-action/issues) page.
