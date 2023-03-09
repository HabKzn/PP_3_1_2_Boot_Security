
async function getResponse() {
    let res = await fetch('/getinfo')
    let content = await res.json()


    let list = document.querySelector('.user-info-table-body')
    let roles = "";
    for (let role in content.roles) {
        roles += `${content.roles[role].name} `
    }

    list.innerHTML += `
       <td>${content.id}</td>
        <td>${content.username}</td>
        <td>${content.lastName}</td>
        <td>${content.age}</td>
        <td>${content.email}</td>
        <td>${roles}</td>
`
}

getResponse();