
async function getResponse() {
    let response = await fetch('/getinfo')
    let json = await response.json()
    let roles = "";
    for (let role in json.roles) {
        roles += `${json.roles[role].name} `
    }
    let table = document.querySelector('.user-info-table-body')
    table.innerHTML += `
       <td>${json.id}</td>
        <td>${json.username}</td>
        <td>${json.lastName}</td>
        <td>${json.age}</td>
        <td>${json.email}</td>
        <td>${roles}</td>
`
}
getResponse();