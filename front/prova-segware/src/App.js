import { useEffect, useState } from 'react';
import './App.css';
const axios = require('axios');

function App() {
  const [posts, setPosts] = useState([])
  const [Author, setAuthor] = useState("")
  const [content, setContent] = useState("")
  const [mensagem, setMensagem] = useState("")

  const CadastroPost = () => {
    axios.post('http://localhost:8081/posts/add', {
      "author": Author,
      "content": content,
      "dataPost": new Date("")
    }).then((response) => {
      console.log(response.data)
      clearCampo()
    }, 1000, []).catch((erro) => {
      setMensagem("Post incompleto")
    });
  }

  const clearCampo = () => {
    setAuthor("");
    setContent("")
    window.location.reload();
  }

  useEffect(() => {
    axios.get('http://localhost:8081/posts')
      .then((response) => {
        setPosts(response.data)
      })
  }, [])

  const Like = async (post) => {
    const totalLike = await axios.patch(`http://localhost:8081/posts/update/${post.id}`)
    setPosts(
      posts.map(post_map => {
        if (post.id === post_map.id) {
          post_map.like = totalLike.data
        }
        return post_map
      })
    )
    window.location.reload();
  }

  return (
    <div className="App">
      <header className="App-header">
        <div style={{ display: 'grid', width: 500, height: 250, top: 20 }}>
          <p style={{ fontSize: 15, padding: 10 }}>Autor do post:</p>
          <input
            type="text" id="Author_name"
            value={Author}
            onChange={(e) => setAuthor(e.target.value)}
            required=""
          />
          <text style={{ fontSize: 15, padding: 10 }}>Post:</text>
          <textarea
            value={content}
            onChange={(e) => setContent(e.target.value)}
            rows="5"
            required=""
          />
          <button onClick={CadastroPost} >Enviar</button>
          <text>{mensagem}</text>
        </div>
        <div style={{ position: 'relative', bottom: -240, width: '100%'}}>
          {posts.map((post) => (
            <div key={post.id} style={{ color: '#000', background: '#4682B4' , padding: 10, bordercolor: '#000', borderStyle: 'solid', borderWidth: 2, margin: 10, minWidth: 200}}>
              <p style={{ fontSize: 15 }}>
                Autor: {post.author}
              </p>
              <p style={{ fontSize: 15 }}>
                publicado em: {post.dataPost}
              </p>
              <p style={{ fontSize: 15, textAlign: 'justify'}}>
                {post.content}
              </p>

              <p style={{ fontSize: 15 }}>
                <button style={{background: '#4682B4', bordercolor: '#000', borderStyle: 'solid', borderWidth: 2, padding: 10}} id={post.id} onClick={() => Like(post)}>Like </button>
                <text> {post.like}</text>
              </p>
            </div>
          ))}
        </div>
      </header>

    </div>
  );
}

export default App;
