const { Client } = require("pg");
const http = require("http");

const client = new Client();
client.connect();

const server = http.createServer(async (req, res) => {
    const result = await client.query("SELECT NOW()");
    res.end("DB Time: " + result.rows[0].now);
});

server.listen(3000, () => {
    console.log("Server running on port 3000");
});
