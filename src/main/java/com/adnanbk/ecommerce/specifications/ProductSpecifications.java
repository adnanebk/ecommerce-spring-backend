package com.adnanbk.ecommerce.specifications;

import com.adnanbk.ecommerce.models.Product;
import lombok.experimental.UtilityClass;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import static com.adnanbk.ecommerce.models.Product.*;


@UtilityClass
public class ProductSpecifications {
    public static Specification<Product> search(String category, String search) {
       return Specification.where(containsNameOrDescription(search).and(containsCategory(category)));
    }

    private static Specification<Product> containsNameOrDescription(String search) {
        return (root, query, criteriaBuilder) -> StringUtils.hasText(search) ?
                criteriaBuilder.or(
                                criteriaBuilder.like(
                                        criteriaBuilder.lower(root.get(Fields.name)),
                                        wrapWithWildcards(search)
                                ),
                                criteriaBuilder.like(
                                        criteriaBuilder.lower(root.get(Fields.description)),
                                        wrapWithWildcards(search)
                                )
                               ) : criteriaBuilder.conjunction();
    }

    private static Specification<Product> containsCategory(String category) {
        return (root, query, criteriaBuilder) -> StringUtils.hasText(category) ?  criteriaBuilder.like(
                criteriaBuilder.lower(root.join(Fields.category).get("name")),
                wrapWithWildcards(category)
        ):criteriaBuilder.conjunction();
    }

    private static String wrapWithWildcards(String input) {
        return "%" + input.toLowerCase() + "%";
    }
}
