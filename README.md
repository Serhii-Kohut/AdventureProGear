<h1>Adventure Pro Gear </h1>


<h2>Launch the project </h2>

<ol>

<h3>Method 1:</h3>
<li>
Clone repository:

```cmd
git clone https://github.com/FilosofDanil/AdventureProGear.git
```
</li>

<li>
For launch of our project we use Docker containers. <br>
Download latest version of Docker:
<a>https://www.docker.com/products/docker-desktop</a>
</li>

<li>
To build the docker image run (in the project directory)
following command:

```cmd
docker build -t adventureprogear .
```


</li>

<li>
To run created docker image run following command:

```cmd
docker run -p 8080:8080 adventureprogear:latest
```

</li>
</ol>

<ol>

<h3>Method 2:</h3>
<li>
Pull shared docker image from dockerHub

```cmd
docker image pull adventureprogear:latest
```

</li>

<li>
To run pulled docker image run following command:

```cmd
docker run -p 8080:8080 adventureprogear:latest
```

</li>

</ol>

