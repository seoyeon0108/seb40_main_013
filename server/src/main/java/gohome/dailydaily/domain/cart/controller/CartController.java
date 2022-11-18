package gohome.dailydaily.domain.cart.controller;

import gohome.dailydaily.domain.cart.dto.ProductCartDto;
import gohome.dailydaily.domain.cart.entity.Cart;
import gohome.dailydaily.domain.cart.entity.ProductCart;
import gohome.dailydaily.domain.cart.mapper.CartMapper;
import gohome.dailydaily.domain.cart.mapper.ProductCartMapper;
import gohome.dailydaily.domain.cart.service.CartService;
import gohome.dailydaily.global.common.security.resolver.MemberId;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carts")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    private final CartMapper mapper;
    private final ProductCartMapper productCartMapper;

    @PostMapping
    public ResponseEntity postProductCart(@MemberId Long memberId,
                                          @RequestBody ProductCartDto.Post productCartDto) {
        ProductCart productCart = productCartMapper.toProductCart(productCartDto);

        Cart cart = cartService.addCart(productCart, memberId);

        return new ResponseEntity<>(mapper.toResponse(cart), HttpStatus.CREATED);
    }

    @DeleteMapping("/{product-cart-id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProductCart(@PathVariable("product-cart-id") Long productCartId,
                                  @MemberId Long memberId) {

        cartService.cancelCart(productCartId, memberId);
    }
}
