# Vindinium client

## Develop / run

```
sbt -Dkey=mySecretKey

Training mode, 30 turns:
> run training 30

Arena mode, 10 games:
> run arena 10
```


## Automatically open chromium browser with game
App has built-in Chromium browser support. When game starts, app will open a new Chromium tab with started game.
Because the feature require particular browser, and wmctrl tool, it is disabled by default.
In order to turn on this feature, set **open_in_browser** environment variable to 'true':
```
sbt -Dopen_in_browser=true -Dkey=mySecretKey 
```

### Prerequisites:
* [Chromium browser](https://www.chromium.org/Home)
* [wmctrl](https://sites.google.com/site/tstyblo/wmctrl)
