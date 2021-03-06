<#macro login path isRegistrForm>
    <form action=${path} method="post">
        <div class="form-group row">
            <label class="col-sm-2 col-form-label"> User Name :</label>
            <div class="col-sm-6">
                <input class="form-control" type="text" name="username" placeholder="User name"/>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-sm-2 col-form-label"> Password:</label>
            <div class="col-sm-6">
                <input class="form-control" type="password" name="password" placeholder="Password"/>
            </div>
        </div>
    <#if isRegistrForm>
        <div class="form-group row">
            <label class="col-sm-2 col-form-label"> E-mail:</label>
            <div class="col-sm-6">
                <input class="form-control" type="email" name="email" placeholder="some@some.com"/>
            </div>
        </div>
    </#if>
        <input type="hidden" name="_csrf" value="${_csrf.token}" />
        <#if !isRegistrForm><a href="/registration">Add new user</a></#if>
        <button class="btn btn-primary" type="submit"><#if isRegistrForm>Create<#else>Sign In</#if></button>
    </form>
</#macro>

<#macro logout>
    <form action="/logout" method="post">
        <input type="hidden" name="_csrf" value="${_csrf.token}" />
        <button class="btn btn-primary" type="submit">Sign out</button>
    </form>
</#macro>