# MysteryKeyService
The mysterykeyservice manages the crafting of keys from key fragments

## Endpoints

### /players/{uuid} [GET]:
#### Gets information about a player

**Response**:
```json
{"keys": 0, "fragments": "2"}
```

### /players/{uuid}/fragments?amount=1 [POST]:
#### Adds fragments, default amount is 1. This will automatically craft a key if possible.

**Response**:
```json
{
"success": "true",
"key_crafted" : "false",
"account": {
  "keys": 1,
  "fragments": 0
}
}
```
### /players/{uuid}/keys?amount=-1 [POST]:
#### Removes a key if possible (default amount -1).

**Response**:
```json
{
"success": "true",
"account": {
  "keys": 0,
  "fragments": 0
}
}
```

```json
{
"success": "false",
"code": 2,
"error": "Insufficient keys.",
"account": {
  "keys": 0,
  "fragments": 2
}
}
```

## Environment
| Name | Value |
| --------- | --- |
| MONGO_URI | {mongo_uri} |
| DB_NAME | {db name to store data} |
