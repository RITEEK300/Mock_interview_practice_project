const token = localStorage.getItem("token");

// LOGIN
document.getElementById("loginForm")?.addEventListener("submit", async (e)=>{
    e.preventDefault();
    const email = document.getElementById("loginEmail").value;
    const password = document.getElementById("loginPassword").value;
    try{
        const res = await fetch("http://localhost:8080/api/auth/login",{
            method:"POST",
            headers: {"Content-Type":"application/json"},
            body: JSON.stringify({email,password})
        });
        const data = await res.json();
        if(res.ok){
            localStorage.setItem("token", data.token);
            window.location.href="profile.html";
        } else alert(data.message);
    }catch(err){ console.log(err); }
});

// PROFILE
if(document.getElementById("profileDetails")){
    (async ()=>{
        try{
            const res = await fetch("http://localhost:8080/api/profile/me",{
                headers: { Authorization: `Bearer ${token}` }
            });
            if(res.ok){
                const user = await res.json();
                document.getElementById("profileDetails").innerText = `${user.name} | ${user.email}`;
            } else window.location.href="index.html";
        }catch(err){ console.log(err); }
    })();
}

// LOGOUT
document.getElementById("logoutBtn")?.addEventListener("click", ()=>{
    localStorage.removeItem("token");
    window.location.href="index.html";
});