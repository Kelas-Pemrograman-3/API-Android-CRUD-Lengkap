const server = require('express')()
const port = 5000

let nilai = 90
// let data = {
//   nama: 'Dika Hastanto',
//   npm: 15411036,
//   jurusan: 'Sistem Informasi',
//   kuliah: (namaMatkul, nama) => {
//     console.log('Selamat Kuliah ' + nama + ', dimatakuliah ' + namaMatkul)
//   }
// }
// data.kuliah('Pemrograman III', data.nama)
let listMhs = [
  {
    nama: 'Dika Hastanto',
    npm: 15411036
  },
  {
    nama: 'Anggun',
    npm: 17411019
  },
  {
    nama: 'Aditya',
    npm: 17411058
  }
]
for (let i = 0; i < listMhs.length; i++) {
  console.log(listMhs[i].nama + ' - ' + listMhs[i].npm)
}
// if (nilai > 80) {
//   console.log('Nilai Anda A')
// } else if (nilai > 70) {
//   console.log('Nilai Anda B')
// } else {
//   console.log('Nilai Anda C')
// }

server.listen(port, function()  {
  console.log('Server started on port ' + port)
})