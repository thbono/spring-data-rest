function createNode(element) {
    return document.createElement(element)
}

function append(parent, el) {
  return parent.appendChild(el)
}

const ul = document.getElementById('people')
const url = 'http://localhost:8080/people'
fetch(url)
.then((resp) => resp.json())
.then(function(data) {
  let people = data._embedded.list
  return people.map(function(person) {
    let li = createNode('li'),
        img = createNode('img'),
        span = createNode('span')
    img.src = `https://randomuser.me/api/portraits/med/men/${person.id}.jpg`
    span.innerHTML = `${person.name}, ${person.age}`
    append(li, img)
    append(li, span)
    append(ul, li)
  })
})
.catch(function(error) {
  console.log(JSON.stringify(error))
});