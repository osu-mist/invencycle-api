# Invencycle

## Overview

A learning project API to test the use of Dropwizard API framework.

## Description

API for bikes, their components, and specifications. 

## GET /

### /bikes

Searches bikes by query for make, model, or type.

### /bikes/{id}

Returns a specific bike specificed by its ID in the request.

## POST /

### /bikes

Creates a new bike record with the components/specifications given by the user in the request.

## PUT /

### /bikes/{id}

Updates the bike specified by the ID. Only updates the bike where attributes are not null. To unchange an attribute of a bike, send a null value for that attribute. Non-null values will update the bike.

## DELETE /

### /bikes/{id}

Deletes a specific bike record in the bike table.