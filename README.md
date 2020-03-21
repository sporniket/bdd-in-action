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

This tutorial project is managed as following :

* A **branch** will focus on a specific stack defined by : the JDK version, the JUnit version, the Spring-boot version.
* The project will use Spring-jersey, in other words JAX-RS (JSR-370). This choice allow an easier migration from other JAX-RS based framework, like RestEASY.
* The **issues** are organised to design the project from scratch. They deal with progressively address the various requirement (in short, be addressable through http protocol, use a database, use external apis), and at the same time, they serve as functionnal specifications with a user story and a test suite. Some issue are purely technical, in order to setup a specific tool to enhance the quality of the codebase.
* In a given branch, each commit deals with an issue, in the same order. In other words, the first commit of the branch deals with the issue #1, the second commit deals with issue #2, and so on...
* **Any commit in the _master_ branch will be followed by a rebase of each tutorial branch !**

### Licence

**bdd-in-action** is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

**bdd-in-action** is freely distributed under the term of copyright laws.

## 2. What should you know before using **bdd-in-action** ?

**bdd-in-action** is a project for a fictionnal company named 'Little Cauldron ltd.' that produces and sells little cauldron for personnal use. It want to build a webstore consisting of a dedicated api to manage its catalog of products. This project will be called 'api-catalog'.

> Do not use **bdd-in-action** if this project is not suitable for your project

## 3. How to use **bdd-in-action** ?

To get the latest available code, one must clone the git repository. Then you may launch all the 'integration test'.

	git clone https://github.com/sporniket/bdd-in-action.git
	cd bdd-in-action
	mvn clean verify

## 4. Known issues
See the [project issues](https://github.com/sporniket/bdd-in-action/issues) page.

## 5. Miscellanous

### Report issues
Use the [project issues](https://github.com/sporniket/bdd-in-action/issues) page.
