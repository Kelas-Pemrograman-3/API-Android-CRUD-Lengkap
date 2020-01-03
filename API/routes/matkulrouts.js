const matakuliah = require('express')()

const matkulcont = require('../controller/matakuliacont')

matakuliah.post('/simpan', (req, res) => {
    console.log(req.body)
    matkulcont.simpandata(req.body)
    .then(result => res.json(result))
    .catch(error => res.json(error))
  })

matakuliah.post('/simpanmkandroid', (req, res) => {
    matkulcont.simpandataMkAndroid(req.body.kodemk,req.body.namamk,req.body.jam,
        req.body.hari,req.body.ruangan,req.body.nidn,req.body.namadosen)
        .then(result => res.json(result))
        .catch(error => res.json(error))
})

matakuliah.put('/updatemkandroid/:id', (req, res) => {
    matkulcont.updateMkAndroid(req.params.id,req.body.kodemk,req.body.namamk,req.body.jam,
        req.body.hari,req.body.ruangan,req.body.nidn,req.body.namadosen)
        .then(result => res.json(result))
        .catch(error => res.json(error))
})

matakuliah.delete('/deletemk/:id', (req, res) => {
    console.log(req.params.id)
    matkulcont.hapusMatakuliah(req.params.id)
        .then(result => res.json(result))
        .catch(error => res.json(error))
})

matakuliah.get('/getmk/:id', (req, res) => {
    console.log(req.params.id)
    matkulcont.getmatakuliahPermatakuliah(req.params.id)
        .then(result => res.json(result))
        .catch(error => res.json(error))
})

  matakuliah.get('/getmk', (req, res) => {
    matkulcont.getmatakuliah()
      .then(result => res.json(result))
      .catch(error => res.json(error))
  })


module.exports = matakuliah
