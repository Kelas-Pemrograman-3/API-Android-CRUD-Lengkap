const mongoose = require('mongoose')
const Schema = mongoose.Schema

const MataKuliah = new Schema({
  kodeMataKuliah: {
    type: String,
    required: true
  },
  namamatakuliah: {
    type: String
  },
  jam: {
    type: String
  },
  hari: {
    type: String
  },
  ruangan: {
    type: String
  }, 
  dosen: [
    {
      nidn: String,
      nama: String
    }
  ]
})

module.exports = mongoose.model('matakuliah', MataKuliah)