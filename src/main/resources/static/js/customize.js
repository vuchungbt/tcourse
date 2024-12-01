
function formatNumberWithDot(num) {
    return num.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ".");
}
function timeAgo(date) {
    const now = new Date();
    const timeDiff = now - new Date(date);
    const seconds = Math.floor(timeDiff / 1000);
    const minutes = Math.floor(seconds / 60);
    const hours = Math.floor(minutes / 60);
    const days = Math.floor(hours / 24);
    const weeks = Math.floor(days / 7);

    if (weeks >= 1) {
        return new Date(date).toLocaleString();
    } else if (days > 0) {
        return `${days} ngày trước`;
    } else if (hours > 0) {
        return `${hours} giờ trước`;
    } else if (minutes > 0) {
        return `${minutes} phút trước`;
    } else {
        return `${seconds} giây trước`;
    }
}
// Hàm cập nhật số lượng giỏ hàng
function updateCartCount() {
    const cartItems = JSON.parse(localStorage.getItem('cartItems')) || [];
    const cartCount = cartItems.length;
    document.getElementById('cart-count').textContent = cartCount;
}

// Hàm hiển thị giỏ hàng trong modal
function displayCartItems() {
    const cartItems = JSON.parse(localStorage.getItem('cartItems')) || [];
    const cartItemsList = document.getElementById('cartItemsList');
    cartItemsList.innerHTML = ''; // Xóa nội dung cũ

    if (cartItems.length === 0) {
        cartItemsList.innerHTML = '<p>Giỏ hàng của bạn hiện tại trống.</p>';
    } else {
        cartItems.forEach(item => {
            const itemElement = document.createElement('div');
            itemElement.classList.add('d-flex', 'justify-content-between', 'mb-3');
            itemElement.innerHTML = `
                            <div>
                                <img src="/image/${item.thumbnail}" alt="${item.name}" width="50px" height="50px" class="me-3">
                                <strong>${item.name}</strong> - ${item.price} VND
                            </div>
                            <div>
                                <button class="btn btn-danger btn-sm" onclick="removeFromCart(${item.postId})">Xóa</button>
                            </div>
                        `;
            cartItemsList.appendChild(itemElement);
        });
    }
}

// Hàm thêm sản phẩm vào giỏ hàng
function addToCart(postId, name, price, thumbnail) {
    const cartItems = JSON.parse(localStorage.getItem('cartItems')) || [];
    const productExists = cartItems.some(item => item.postId === postId);

    if (productExists) {
        alert('Sản phẩm đã có trong giỏ hàng.');
        return; // Không thêm nếu sản phẩm đã tồn tại
    }

    const newProduct = { postId, name, price, thumbnail };
    cartItems.push(newProduct);
    localStorage.setItem('cartItems', JSON.stringify(cartItems));
    updateCartCount();
}

// Hàm xóa sản phẩm khỏi giỏ hàng
function removeFromCart(postId) {
    console.log('postId:' ,postId);
    let cartItems = JSON.parse(localStorage.getItem('cartItems')) || [];
    console.log('cartItems1:' ,cartItems);
    cartItems = cartItems.filter(item => String(item.postId) !== String(postId));
    console.log('cartItems1:' ,cartItems);
    localStorage.setItem('cartItems', JSON.stringify(cartItems));
    updateCartCount();
    displayCartItems(); // Cập nhật lại giỏ hàng trong modal
}

// Hàm xóa tất cả sản phẩm khỏi giỏ hàng
function clearCart() {
    localStorage.removeItem('cartItems');
    updateCartCount();
    displayCartItems(); // Cập nhật lại giỏ hàng trong modal
}

// Hàm xử lý khi người dùng nhấn nút "Xem giỏ hàng"
document.getElementById('viewCartBtn').addEventListener('click', function () {
    displayCartItems();
    const cartModal = new bootstrap.Modal(document.getElementById('cartModal'));
    cartModal.show();
});

// Gán sự kiện cho nút "Xóa giỏ hàng"
document.getElementById('clearCartBtn').addEventListener('click', function () {
    clearCart();
});

document.addEventListener('DOMContentLoaded', function () {

    const addToCartButtons = document.querySelectorAll('.add-to-cart-btn');

    addToCartButtons.forEach(button => {
        button.addEventListener('click', function () {
            const postId = button.getAttribute('data-post-id');
            const name = button.getAttribute('data-name');
            const price = button.getAttribute('data-price');
            const thumbnail = button.getAttribute('data-thumbnail');
            addToCart(postId, name, price, thumbnail);
        });
    });

    // Cập nhật số lượng giỏ hàng khi trang được tải
    updateCartCount();

    const elements = document.querySelectorAll(".num");
    elements.forEach(el => {
        const text = el.textContent.trim(); // Lấy nội dung văn bản
        const [value, unit] = text.split(" "); // Tách số và đơn vị
        const formattedValue = formatNumberWithDot(value); // Định dạng số
        el.textContent = `${formattedValue} ${unit || ""}`; // Ghép lại số và đơn vị
    });

    const timestamps = document.querySelectorAll(".timestamps");
    console.log('timestamps: ', timestamps)
    timestamps.forEach(timestamp => {
        console.log('timestamp: ', timestamp)
        const originalText = timestamp.textContent;
        timestamp.textContent = timeAgo(originalText);
    });
    document.querySelectorAll('.dropdown-submenu > a').forEach(function (element) {
        element.addEventListener('click', function (e) {
            e.preventDefault();
            e.stopPropagation();
            var submenu = this.nextElementSibling;

            var allSubmenus = document.querySelectorAll('.dropdown-submenu .dropdown-menu');
            allSubmenus.forEach(function (menu) {
                if (menu !== submenu) {
                    menu.style.display = 'none';
                }
            });

            if (submenu.style.display === 'block') {
                submenu.style.display = 'none';
            } else {
                submenu.style.display = 'block';
            }
        });
    });
});