// Danh sách sách mẫu
let books = [
    { id: 1, name: "The Lord Of The Ring: The Two Towers", price: 567000, category: "Tiểu thuyết", image: "https://salt.tikicdn.com/cache/750x750/ts/product/a8/fe/98/acc45d6ac4b3ebea246fb8ae51d9b6ef.jpg.webp" },
    { id: 2, name: "Khởi Nghiệp 4.0 - Kinh Doanh Thông Minh Trong Cách Mạng Công Nghiệp 4.0", price: 139000, category: "Kinh tế", image: "https://salt.tikicdn.com/cache/750x750/ts/product/c6/94/39/7e9ab3cd4a2fe69c8cd813ee0c45434d.jpg.webp" },
    { id: 3, name: "Harry Potter Và Hòn Đá Phù Thủy: Tập 01", price: 135000, category: "Tiểu thuyết", image: "https://salt.tikicdn.com/cache/750x750/ts/product/d4/56/6c/35c19da940c8c805337e57f48ab1b18c.jpg.webp" },
    { id: 4, name: "Thám Tử Lừng Danh Conan Tập 80", price: 24000, category: "Truyện tranh", image: "https://salt.tikicdn.com/cache/w300/ts/product/6b/a5/86/8f231a6f31e6630e728ad2afd50bbbd2.jpg.webp" },
    { id: 5, name: "Sách Giáo Khoa Toán 12 Tập 2 - Chân Trời Sáng Tạo", price: 25000, category: "Sách giáo khoa", image: "https://salt.tikicdn.com/cache/750x750/ts/product/ee/0f/b8/3ced621501e5d338fc24eaf69333280a.png.webp" }
];

let cart = [];
let cartCount = 0;
let users = JSON.parse(localStorage.getItem('users')) || [];

function displayBooks(booksToShow) {
    const productGrid = document.getElementById("productGrid");
    productGrid.innerHTML = "";
    booksToShow.forEach(book => {
        const card = document.createElement("div");
        card.className = "product-card";
        card.innerHTML = `
            <img src="${book.image}" alt="${book.name}">
            <div class="product-info">
                <h3>${book.name}</h3>
                <p>${book.price.toLocaleString()} VNĐ</p>
                <button onclick="addToCart(${book.id})">Thêm vào giỏ</button>
            </div>
        `;
        productGrid.appendChild(card);
    });
}

function searchBooks() {
    const searchTerm = document.getElementById("searchInput").value.toLowerCase();
    const filteredBooks = books.filter(book => book.name.toLowerCase().includes(searchTerm));
    displayBooks(filteredBooks);
}

function filterBooks() {
    const category = document.getElementById("categoryFilter").value;
    const filteredBooks = category === "all" ? books : books.filter(book => book.category === category);
    displayBooks(filteredBooks);
}

function addToCart(bookId) {
    const book = books.find(b => b.id === bookId);
    cart.push(book);
    cartCount++;
    document.querySelector(".cart-login a").textContent = `Giỏ Hàng (${cartCount})`;
    updateCart();
}

function updateCart() {
    const cartItems = document.getElementById("cartItems");
    cartItems.innerHTML = "";
    let total = 0;
    cart.forEach(item => {
        const itemDiv = document.createElement("div");
        itemDiv.className = "cart-item";
        itemDiv.innerHTML = `
            ${item.name} - ${item.price.toLocaleString()} VNĐ
            <button onclick="removeFromCart(${item.id})">Xóa</button>
        `;
        cartItems.appendChild(itemDiv);
        total += item.price;
    });
    document.getElementById("cartTotal").textContent = `Tổng: ${total.toLocaleString()} VNĐ`;
    toggleCart();
}

function removeFromCart(bookId) {
    const index = cart.findIndex(item => item.id === bookId);
    if (index !== -1) {
        cart.splice(index, 1);
        cartCount--;
        document.querySelector(".cart-login a").textContent = `Giỏ Hàng (${cartCount})`;
        updateCart();
    }
}

function toggleCart() {
    const cart = document.getElementById("cart");
    cart.style.display = cart.style.display === "block" ? "none" : "block";
}

function toggleAuthModal() {
    const modal = document.getElementById("authModal");
    modal.style.display = modal.style.display === "flex" ? "none" : "flex";
    showLoginForm();
}

function showLoginForm() {
    document.getElementById("loginForm").style.display = "block";
    document.getElementById("registerForm").style.display = "none";
}

function showRegisterForm() {
    document.getElementById("loginForm").style.display = "none";
    document.getElementById("registerForm").style.display = "block";
}

function register() {
    const hoTen = document.getElementById("regHoTen").value;
    const soDienThoai = document.getElementById("regSoDienThoai").value;
    const email = document.getElementById("regEmail").value;
    const password = document.getElementById("regPassword").value;
    const gender = document.querySelector('input[name="gender"]:checked')?.value;
    const ngaySinh = document.getElementById("regNgaySinh").value;

    if (!hoTen || !soDienThoai || !email || !password || !gender || !ngaySinh) {
        alert("Vui lòng điền đầy đủ thông tin!");
        return;
    }

    // Giả lập gửi email xác thực
    const verificationCode = 1;
    const enteredCode = prompt(`Một mã xác thực đã được gửi đến ${email}. Vui lòng nhập mã:`);
    
    if (enteredCode === verificationCode) {
        const user = { hoTen, soDienThoai, email, password, gender, ngaySinh, verified: true };
        users.push(user);
        localStorage.setItem('users', JSON.stringify(users));
        alert("Đăng ký thành công! Vui lòng đăng nhập.");
        showLoginForm();
        clearRegisterForm();
    } else {
        alert("Mã xác thực không đúng! Vui lòng thử lại.");
    }
}

function clearRegisterForm() {
    document.getElementById("regHoTen").value = "";
    document.getElementById("regSoDienThoai").value = "";
    document.getElementById("regEmail").value = "";
    document.getElementById("regPassword").value = "";
    const genderInputs = document.querySelectorAll('input[name="gender"]');
    genderInputs.forEach(input => {
        input.checked = false;
    });
    document.getElementById("regNgaySinh").value = "";
}

function login() {
    const email = document.getElementById("loginEmail").value;
    const password = document.getElementById("loginPassword").value;
    const user = users.find(u => u.email === email && u.password === password && u.verified);

    if (user) {
        alert(`Đăng nhập thành công với ${email}!`);
        toggleAuthModal();
        document.querySelector(".cart-login button").textContent = "Đăng Xuất";
        document.querySelector(".cart-login button").onclick = logout;
    } else {
        alert("Email hoặc mật khẩu không đúng! Vui lòng thử lại.");
    }
}

function logout() {
    if (confirm("Bạn có chắc muốn đăng xuất?")) {
        alert("Đăng xuất thành công!");
        document.querySelector(".cart-login button").textContent = "Đăng Nhập/Đăng Ký";
        document.querySelector(".cart-login button").onclick = toggleAuthModal;
    }
}

function placeOrder() {
    if (cart.length === 0) {
        alert("Giỏ hàng trống!");
        return;
    }
    if (document.querySelector(".cart-login button").textContent === "Đăng Nhập/Đăng Ký") {
        alert("Vui lòng đăng nhập để đặt hàng!");
        toggleAuthModal();
        return;
    }
    const total = cart.reduce((sum, item) => sum + item.price, 0);
    alert(`Đặt hàng thành công! Tổng tiền: ${total.toLocaleString()} VNĐ. Vui lòng kiểm tra email để hoàn tất.`);
    cart = [];
    cartCount = 0;
    document.querySelector(".cart-login a").textContent = `Giỏ Hàng (${cartCount})`;
    updateCart();
}

// Thêm nút đặt hàng vào giỏ hàng
document.getElementById("cart").innerHTML += '<button onclick="placeOrder()" style="margin-top: 10px; padding: 10px 20px; background-color: #2ecc71; color: #fff; border: none; border-radius: 20px; cursor: pointer;">Đặt Hàng</button>';

// Khởi tạo
displayBooks(books);
document.getElementById("cart").style.display = "none";