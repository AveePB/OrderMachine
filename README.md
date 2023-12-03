# McDonald's Order Machine

## Table of contents
1. [Introduction](#introduction)
2. [Representational State Transfer "REST"](#rest)
3. [Create, Read, Update and Delete "CRUD"](#crud)
5. [RESTful Endpoints](#restful_endpoints)
    - [GET Method](#get_method)
    - [POST Method](#post_method)
    - [PUT Method](#put_method)
    - [DELETE Method](#delete_method)

## Introduction <a name="introduction"></a>
Welcome to the Spring Boot McDonald's Order Machine project! In this endeavor, we delve into the realm of 
software development using Spring Boot to create a streamlined and efficient ordering system inspired by 
the world-renowned McDonald's.

This project serves as an immersive learning experience, providing a platform to grasp fundamental skills 
in software development. Through the utilization of Spring Boot, a powerful framework for building Java-based 
applications, we aim to craft a functional and user-friendly ordering system.

## Representational State Transfer "REST" <a name="rest"></a>
Representational State Transfer (REST) is an architectural style that defines a set of constraints to be used 
for creating web services. REST API is a way of accessing web services in a simple and flexible way without
having any processing. Web application's data might be accessed via an API, and are often stored in a persistent 
data store, such as a database.

## Create, Read, Update and Delete "CRUD" <a name="crud"></a>
A frequently mentioned concept when speaking about REST is CRUD. CRUD stands for “Create, Read, Update, and Delete”.
These are the four basic operations that can be performed on objects in a data store. We’ll learn that REST has 
specific guidelines for implementing each one.

| Operation | HTTP Method | Response Status |
| Read      | GET         | 200 (OK)        |
| Create    | POST        | 201 (CREATED)   |
| Update    | PUT         | 204 (NO DATA)   |
| Delete    | DELETE      | 204 (NO DATA)   |

## RESTful Endpoints <a name="restful_endpoints"></a>


### GET Method <a name="get_method"></a>
