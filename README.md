# Postman Requests

```
Create Event

curl --location 'http://localhost:8081/api/v1/events' \
--header 'X-Customer-Id: fatih' \
--header 'Content-Type: application/json' \
--data '{
  "league": "Süper Lig",
  "homeTeam": "Galatasaray",
  "awayTeam": "Fenerbahçe",
  "homeWinRate": 1.85,
  "drawRate": 3.10,
  "awayWinRate": 2.45,
  "startTime": "2025-04-03T21:45:00"
}
```

```
Get All Events
 
curl --location 'http://localhost:8081/api/v1/events' \
--header 'X-Customer-Id: fatih'

```

```
Create Bet Slip

curl --location 'http://localhost:8081/api/v1/bet-slips' \
--header 'X-Customer-Id: fatih' \
--header 'Content-Type: application/json' \
--data '{
  "eventId": 1,
  "betType": "AWAY_WIN",
  "betAmount": 100,
  "quantity": 40,
  "betRate": "2.50"
}
```

