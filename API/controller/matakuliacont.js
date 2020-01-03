const matakuliahmod = require('../models/matakuliah')
const mongoose = require('mongoose')
const ObjectId = mongoose.Types.ObjectId;

exports.simpandata = (data) =>
new Promise((resolve, reject) => {
  matakuliahmod.create(data)
  .then(res => {
    resolve ({error: false,
    pesan: 'Data Terimput'})
  })
  .catch(res => {
    reject({
      error: true,
      pesan: 'Gaagal Terimput'
    })
  })
})


exports.simpandataMkAndroid = (kodeMk, namaMk, jam, hari,
                        ruangan, nidn, namaDosen) =>
    new Promise((resolve, reject) => {

      let dataMk = new matakuliahmod({
        kodeMataKuliah: kodeMk,
        namamatakuliah: namaMk,
        jam: jam,
        hari: hari,
        ruangan:ruangan,
        dosen: [
          {
            nidn: nidn,
            nama: namaDosen
          }
        ]
      });

      matakuliahmod.create(dataMk)
          .then(res => {
            resolve ({error: false,
              pesan: 'Data Terimput'})
          })
          .catch(res => {
            reject({
              error: true,
              pesan: 'Gaagal Terimput'
            })
          })
    })


exports.updateMkAndroid = (id,kodeMk, namaMk, jam, hari,
                               ruangan, nidn, namaDosen) =>
    new Promise(async (resolve, reject) => {

      // let query = {_id: ObjectId(req.params.id)};
      // console.log(dataMk)
      console.log("disini nih")

      await matakuliahmod.update(
          { _id: ObjectId(id) },
          {
            $set: {
              kodeMataKuliah: kodeMk,
              namamatakuliah: namaMk,
              jam: jam,
              hari: hari,
              ruangan:ruangan,
              dosen: [
                {
                  nidn: nidn,
                  nama: namaDosen
                }
              ]
            }
          })
          .then(res => {
            resolve ({error: false,
              pesan: 'Data Berhasil Dirubah'})
          })
          .catch(res => {
            console.log("disini")
            reject({
              error: true,
              pesan: 'Gaagal Dirubah'
            })
          })
    })


exports.hapusMatakuliah = (id) =>
    new Promise(async (resolve, reject) => {
      await matakuliahmod.remove({_id: ObjectId(id)})
          .then(res => {
            resolve ({error:false, pesan: 'Data Berhasil hapus'})
          })
          .catch(res => {
            console.log(error)
            reject ({error:true, pesan: 'Data gagal dihapus'})
          })
    })


exports.getmatakuliahPermatakuliah = (mk) =>
    new Promise((resolve, reject) => {
      matakuliahmod.findOne({kodeMataKuliah: mk})
          .then(res => {
            resolve ({error:false, pesan: 'Data Berhasil diambil', data: res})
          })
          .catch(res => {
            console.log(error)
            reject ({error:true, pesan: 'Data Tidak diambil'})
          })
    })

exports.getmatakuliah = () =>
new Promise((resolve, reject) => {
  matakuliahmod.find()
  .then(res => {
    resolve ({error:false, pesan: 'Data Berhasil diambil', data: res})
  })
  .catch(res => {
    console.log(error)
    reject ({error:true, pesan: 'Data Tidak diambil'})
  })
})
