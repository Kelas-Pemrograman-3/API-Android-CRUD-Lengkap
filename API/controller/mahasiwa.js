const mahasiswaModel = require('../models/mahasiswa')
const bcrypt = require('bcryptjs')

exports.insertMahasiswa = (data) =>
  new Promise((resolve, reject) => {
    bcrypt.hash(data.password, 10, (err, hash) => {
      data.password = hash
      mahasiswaModel.find({
        npm: data.npm
      }).then(hasil => {
        if (hasil.length > 0) {
          reject({
            error: true,
            pesan: 'NPM Sudah Digunakan'
          })
        } else {
          mahasiswaModel.create(data)
            .then(res => {
              resolve({
                error: false,
                pesan: 'Berhasil Input Data'
              })
            }).catch(() => {
              reject({
                error: true,
                pesan: 'NPM Sudah Digunakan.'
              })
            })
        }
      })
    })
  })

exports.login = (data) =>
  new Promise((resolve, reject) => {
    mahasiswaModel.findOne({
      npm: data.npm
    }).then(res => {
      if (res === null) {
        reject({
          error: true,
          pesan: 'Username Tidak Terdaftar'
        })
      } else {
        let passwordHash = res.password
        if (bcrypt.compareSync(data.password, passwordHash)) {
          resolve({
            error: false,
            pesan: 'Berhasil Login',
            data: res
          })
        } else {
          reject({
            error: true,
            pesan: 'Password Anda Salah'
          })
        }
      }
    })
  })

exports.getAllMahasiswa = () =>
    new Promise((resolve, reject) => {
        mahasiswaModel.find()
            .then(results => {
                resolve({
                    error: false,
                    pesan: 'Berhasil Mengambil Data',
                    data: results
                })
            }).catch(() => {
            reject({
                error: true,
                pesan: 'Gagal Mengambil Data'
            })
        })
    })
