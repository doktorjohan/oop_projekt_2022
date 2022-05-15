# Objektorienteeritud programmeerimine rühmatöö
## IOT-controller server
***
### Võimalused:
- Mitmete klientide samaegne ühendus non-blocking IO abil
  - Lisaks kasutab server väga vähe ressurssi
- Lihtne lisada erinevaid andmekanaleid (**controllers**) (nagu näiteks erinevad IoT seadmed, kaasaarvatud Raspberry Pi, Ardunio jne)
  - Muidugi on võimalus andmeid lugeda ka mujalt, nagu näiteks failidest, internetist, you name it
  - Edasiarendusena saaksid andmete edastajad ja vastuvõtjad olla ka erinevad seadmed (tänu eraldatud andmekirjutajatele (**writers**))
- Lihtne lisada andemtöötlusblokke (**processors**) soovitud vastuste saamiseks
  - Kanalid viivad andmed universaalkujule (**message**), seega saab kõike andmeid töödelda samade protsessoritega

### Tööülevaade:
##### Socketid
1. Socket hoiab ühendunud seadme/kliendi kohta kõike vajalikku infot, nagu näiteks:
   1. Controller, mis haldab, kuidas socketist informatsiooni lugeda saaks
   2. Processor, mis ütleb, kuidas saadud andmeid töödelda
   3. Writer, mis haldab, kuidas töödeldud info kliendile tagasi põrgatada
##### Server
1. Käivitatakse Server, mis loob kaks lõime: socketite vastuvõtmiseks ning socketite töötlemiseks
2. Vastuvõtja on oma olemuselt päris loll, aga väga vajalik: vaatab, kas keegi tahab serveriga ühendust saada ning lisab nad queue-sse, mida teine lõim aktiivselt loeb
3. Teine lõim ehk protsessor on see-eest aluseks kõigele targale. Iga 100ms läbib ta järgmist protsessideahelat:
   1. Võtab vastu eelnevalt mainitud queue-s olevad socketid (SocketProcessor.addSockets)
   2. Loeb olemasolul olemasolevatest socketitest info (SocketProcessor.readSockets)
   3. Töötleb kõike saadud infot (SocketProcessor.processSockets)
