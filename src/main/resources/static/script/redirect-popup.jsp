<html xmlns:th="http://www.thymeleaf.org" lang="ko">
<head>
    <title>MES</title>
</head>
<body>
<script type="text/javascript" th:inline="javascript">
    /*<![CDATA[*/
    /* Thymeleaf 내부에서 JavaScript를 작성하려면 th:inline="javascript"를 사용하십시오. */
    /* target이 null이 아닌 경우 window.location.href를 설정합니다. */
    var target = /*[[${target}]]*/ null;
    if (target !== null) {
        window.location.href = target;
        window.close();
    }
    /*]]>*/
</script>
</body>
</html>