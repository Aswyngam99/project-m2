require('dotenv').config()
var cors = require('cors')

const express = require('express')
const mongoose = require('mongoose')
const objectsRoutes = require('./routes/objects')


// create express app
const app = express()
app.use(cors())

// middleware
app.use(express.json())

app.use((req, res, next) => {
console.log(req.path, req.method)
next()
})

//routes
// req : info req res : repsponse back to the client
app.use('/api/objects',objectsRoutes)

// connect to db
mongoose.connect(process.env.MONG_URI)
    .then(() =>{
        //listen for requests
    app.listen(process.env.PORT, () => {
    console.log('connected to db & listenning on port',process.env.PORT)
})
    })
    .catch((error) => {
        console.log(error)
    })

